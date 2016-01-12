package com.github.dunno.concurrent.thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang.he on 16/1/12.
 * 使用ThreadGroup,我感觉没有什么卵用
 */
public class TestThreadGroup {

	@Test
	public void testMain() {
		ThreadGroup threadGroup = new ThreadGroup("test thread group");

		List<Thread> list = new ArrayList<Thread>();
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(threadGroup, new Runnable() {
				@Override
				public void run() {
					System.out.println("i'm a thread");
				}
			});
			thread.setName("test thread " + i);
			list.add(thread);
		}

		for (Thread thread : list) {
			thread.start();
			System.out.println(threadGroup.activeCount());
		}

		for (Thread thread : list) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
