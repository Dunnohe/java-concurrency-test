package com.github.dunno.concurrent.thread;

import org.junit.Test;

/**
 * Created by liang.he on 16/1/12.
 * 测试线程全局为捕捉的异常
 */
public class TestCatchExceptionInThread {

	class ThreadExceptionHandle implements Thread.UncaughtExceptionHandler {
		@Override
		public void uncaughtException(Thread t, Throwable e) {
			System.out.println("occurs error");
		}
	}

	@Test
	public void testMain() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				throw new RuntimeException("runtime exception");
			}
		});
		thread.setUncaughtExceptionHandler(new ThreadExceptionHandle());
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
