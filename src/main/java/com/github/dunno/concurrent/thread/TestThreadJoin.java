package com.github.dunno.concurrent.thread;

import org.junit.Test;

/**
 * Created by liang.he on 16/1/12.
 */
public class TestThreadJoin extends Thread{
	@Override
	public void run() {
		int sleep = 5;

		while ( sleep > 0) {
			try {
				Thread.sleep(1000);
				System.out.println("i have sleep 1 secends!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sleep --;
		}

		System.out.println("i 'm wake up");

	}

	@Test
	public void testMain() {
		TestThreadJoin testThreadJoin = new TestThreadJoin();
		testThreadJoin.start();
		try {
			testThreadJoin.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		TestThreadJoin testThreadJoin1 = new TestThreadJoin();
		testThreadJoin1.start();
		try {
			testThreadJoin1.join(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
