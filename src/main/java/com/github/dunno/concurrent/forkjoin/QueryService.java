package com.github.dunno.concurrent.forkjoin;

/**
 * Created by liang.he on 16/1/11.
 */
public class QueryService implements Runnable {
	private int sleepTime;

	public QueryService(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	@Override
	public void run() {
		System.out.println();
		System.out.println("name : " + Thread.currentThread().getName() + " start to process!");
		try {
			Thread.sleep(sleepTime * 10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("name : " + Thread.currentThread().getName() + " finish! cost:" + sleepTime + "s");
	}
}
