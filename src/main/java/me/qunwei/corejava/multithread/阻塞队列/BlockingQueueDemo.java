package me.qunwei.corejava.multithread.阻塞队列;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by qunwei on 16-5-18.
 */
public class BlockingQueueDemo { //阻塞队列法  搜索文件/文件夹 中的关键词数量
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter base directory:");
        String directory = in.nextLine();
        System.out.println("Enter your key word:");
        String keyWord = in.nextLine();

        final int FILE_QUEUE_SIZE = 10;
        final int SEARCH_THREADS = 100;

        BlockingQueue<File> queue = new ArrayBlockingQueue<File>(FILE_QUEUE_SIZE);

        FileEnumerationTask enumerator = new FileEnumerationTask(queue,new File(directory));

        new Thread(enumerator).start();
        for (int i = 0; i < SEARCH_THREADS; i++) {
            new Thread(new SearchTask(queue,keyWord)).start();
        }

    }
}


//给队列添加任务
class FileEnumerationTask implements Runnable{

    public static File DUMMY = new File("");
    private BlockingQueue<File> queue;
    private File startingDirectory;

    public FileEnumerationTask(BlockingQueue<File> queue, File startingDirectory) {
        this.queue = queue;
        this.startingDirectory = startingDirectory;
    }

    public void run() {
        try {
            enumerate(startingDirectory);
            queue.put(DUMMY);
        }catch (InterruptedException e){

        }
    }

    public void enumerate(File directory) throws InterruptedException {
        File[] files = directory.listFiles();
        for (File file:files) {
            if (file.isDirectory()){
                enumerate(file);
            }else {
                queue.put(file);
            }
        }
    }
}

//多线程同时处理任务  用一个同步阻塞队列,分配任务
class SearchTask implements Runnable{

    private BlockingQueue<File> queue;
    private String keyWord;

    public SearchTask(BlockingQueue<File> queue, String keyWord) {
        this.queue = queue;
        this.keyWord = keyWord;
    }

    public void run() {
        try {
            boolean done = false;
            while (!done){
                File file = queue.take();
                if (file == FileEnumerationTask.DUMMY){
                    queue.put(file);
                    done = true;
                }else {
                    search(file);
                }
            }
        } catch (InterruptedException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void search(File file) throws IOException{
        try (Scanner in = new Scanner(file)){
            int lineNumber = 0;
            while (in.hasNextLine()){
                lineNumber++;
                String line = in.nextLine();
                if (line.contains(keyWord)){
                    System.out.printf("%s:%d:%s%n",file.getPath(),lineNumber,line);
                }
            }
        }
    }
}