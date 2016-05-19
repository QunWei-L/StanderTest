package me.qunwei.corejava.multithread;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by qunwei on 16-5-20.
 */
public class ThreadPoolDemo { //线程池法
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter base directory:");
        String directory = in.nextLine();
        System.out.println("Enter key word:");
        String keyWord = in.nextLine();

        ExecutorService pool = Executors.newCachedThreadPool();

        MatchCounterWithPool counter = new MatchCounterWithPool(new File(directory),keyWord,pool);

        Future<Integer> result = pool.submit(counter);

        try {
            System.out.println(result.get() +" matching files. ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        pool.shutdown();

        int largestPoolSize = ((ThreadPoolExecutor)pool).getLargestPoolSize();
        System.out.println("largest pool size:"+largestPoolSize);
    }
}


class MatchCounterWithPool implements Callable<Integer> {

    public static final CCC nums = new CCC();
    private File directory;
    private String keyWord;
    private int count;
    private ExecutorService pool;

    public MatchCounterWithPool(File directory, String keyWord,ExecutorService pool) {
        this.directory = directory;
        this.keyWord = keyWord;
        this.pool = pool;
    }

    @Override
    public Integer call() throws Exception {

        count = 0;

        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    MatchCounterWithPool counter = new MatchCounterWithPool(file, keyWord,pool);
                    Future<Integer> result = pool.submit(counter);
                    results.add(result);

                } else {
                    if (search(file))
                        count++;
                }
            }
            for (Future<Integer> result : results) {
                try {
                    count += result.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {

        }
        return count;
    }

    private boolean search(File file) {
        try {
            try (Scanner in = new Scanner(file)){
                boolean found = false;
                while (!found && in.hasNextLine()){
                    String line = in.nextLine();
                    if (line.contains(keyWord)) found = true;
                }
                return found;
            }
        }catch (IOException e){
            return false;
        }
    }
}