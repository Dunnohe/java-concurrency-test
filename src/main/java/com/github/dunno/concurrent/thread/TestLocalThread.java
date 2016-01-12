package com.github.dunno.concurrent.thread;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang.he on 16/1/12.
 */
public class TestLocalThread {

	private ThreadLocal<Integer> local = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 0;
		}
	};

	class TestThread extends Thread {

		@Override
		public void run() {
			Integer num = local.get();
			num = num + 1;
			local.set(num);
			print();
		}

		public void print() {
			System.out.println(Thread.currentThread().getName() + "-" + local.get());
		}
	}

	@Test
	public void testMain() {
		List<TestThread> threadList = new ArrayList<TestThread>();
		for (int i = 0; i < 100; i++) {
			TestThread thread = new TestThread();
			thread.setName("test-" + i);
			threadList.add(thread);
		}

		for (TestThread thread : threadList) {
			thread.start();
		}

		for (TestThread thread : threadList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (TestThread thread : threadList) {
			thread.print();
		}


	}
}
