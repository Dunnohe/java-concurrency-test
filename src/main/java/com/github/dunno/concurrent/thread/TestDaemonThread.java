package com.github.dunno.concurrent.thread;


/**
 * Created by liang.he on 16/1/12.
 *
 * 当所有不是守护线程的线程执行结束时，程序就会退出
 */
public class TestDaemonThread {

	public static void main(String[] args) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				int sleep = 2;

				while ( sleep > 0) {
					try {
						Thread.sleep(1000);
						System.out.println(Thread.currentThread().getName() + "i have sleep 1 secends!");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sleep --;
				}

				System.out.println(Thread.currentThread().getName() + "i 'm wake up");
			}
		});
		thread.setName("not daemon thread ");

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				int sleep = 5;

				while ( sleep > 0) {
					try {
						Thread.sleep(1000);
						System.out.println(Thread.currentThread().getName() + "i have sleep 1 secends!");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sleep --;
				}

				System.out.println(Thread.currentThread().getName() + "i 'm wake up");
			}
		});
		thread2.setName("daemon thread ");
		thread2.setDaemon(true);

		thread.start();
		thread2.start();

		try {
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(thread.getName() + "pro " + thread.getPriority());
		System.out.println(thread2.getName() + "pro " + thread2.getPriority());

	}
}
