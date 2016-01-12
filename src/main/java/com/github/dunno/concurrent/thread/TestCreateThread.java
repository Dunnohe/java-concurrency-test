package com.github.dunno.concurrent.thread;

/**
 * Created by liang.he on 16/1/12.
 */
public class TestCreateThread implements Runnable {

	private int a = 0;

	private int b = 0;

	public TestCreateThread(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public int calc(int a, int b) {
		return a * b;
	}

	@Override
	public void run() {
		System.out.println();
		System.err.printf("%s: %s", Thread.
				currentThread().getName(), Thread.
				currentThread().getState());
		int calc = calc(a, b);
		System.err.printf("%s: %d * %d = %d\n", Thread.
				currentThread().getName(), a, b, calc);
	}

	public static void main(String[] args) {
		Thread thread = new Thread(new TestCreateThread(2, 4));
		thread.setName("calc thread " + thread.getId());
		System.out.println();
		System.err.printf("%s: %s", thread.getName(), thread.getState());
		thread.start();
		System.out.println();
		System.err.printf("%s: %s", thread.getName(), thread.getState());
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.err.printf("%s: %s", thread.getName(), thread.getId());
		System.out.println();
		System.err.printf("%s: %s", thread.getName(), thread.isDaemon());
		System.out.println();
		System.err.printf("%s: %s", thread.getName(), thread.isAlive());
		System.out.println();
		System.err.println(thread.getName() + "pro " + thread.getPriority());
		System.out.println();
		System.err.printf("%s: %s", thread.getName(), thread.getState());

	}
}
