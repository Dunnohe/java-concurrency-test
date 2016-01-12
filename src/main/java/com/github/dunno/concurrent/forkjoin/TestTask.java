package com.github.dunno.concurrent.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by liang.he on 16/1/10.
 * future
 */
public class TestTask extends RecursiveTask {

	private int sleepTime = 0;

	public TestTask(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	@Override
	protected Object compute() {
		if(sleepTime > 2) {
			int subSleepTime = sleepTime / 2;
			int subSleepTime2 = sleepTime - subSleepTime;
			invokeAll(new TestTask(subSleepTime), new TestTask(subSleepTime2));
		} else {
			Thread thread = new Thread(new QueryService(sleepTime));
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		TestTask task = new TestTask(20);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(task);
		do {
			System.out.println("total cost:" + (System.currentTimeMillis() - start));
		} while (task.isDone());

	}
}
