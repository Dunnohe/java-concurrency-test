package com.github.dunno.concurrent.thread;

/**
 * Created by liang.he on 16/1/12.
 * 测试打断一个线程
 */
public class TestInterruptThread extends Thread{

	private boolean flag = true;

	@Override
	public void run() {
		while (true) {
			System.out.println("i'm still running!");
			if (isInterrupted()) {
				System.out.println("The Prime Generator has been Interrupted");
				return;
			}
		}
	}

	public static void main(String[] args ) {
		TestInterruptThread task = new TestInterruptThread();
		task.start();
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		task.interrupt();
	}
}
