package me.qunwei.corejava.multithread.异步计算Future;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by qunwei on 16-5-19.
 */
public class FutureDemo {//异步计算 Callable和Future,一个产生结果,一个拿到结果 搜索关键词

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter base directory:");
        String directory = in.nextLine();
        System.out.println("Enter key word:");
        String keyWord = in.nextLine();

        MatchCounter counter = new MatchCounter(new File(directory),keyWord);
        FutureTask<Integer> task = new FutureTask<Integer>(counter);
        Thread t = new Thread(task);
        t.start();

        try {
            System.out.println(task.get() + "matching files.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


}
class CCC{
    private static int counter =0;

    public int getCounter() {
        return counter;
    }

    public void up(){
        counter++;
    }



}
class MatchCounter implements Callable<Integer> {

    public static final CCC nums = new CCC();
	private File directory;
	private String keyWord;
	private int count;

	public MatchCounter(File directory, String keyWord) {
		this.directory = directory;
		this.keyWord = keyWord;
	}

	@Override
	public Integer call() throws Exception {

		count = 0;

		try {
			File[] files = directory.listFiles();
			List<Future<Integer>> results = new ArrayList<>();
			for (File file : files) {
				if (file.isDirectory()) {
					MatchCounter counter = new MatchCounter(file, keyWord);
					FutureTask<Integer> task = new FutureTask<Integer>(counter);
					results.add(task);
					Thread t = new Thread(task);

                    synchronized (MatchCounter.nums){

                        System.out.println("Thread :"+nums.getCounter()+"\t Start!");
                        nums.up();
                    }
                    t.start();

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
