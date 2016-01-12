package com.github.dunno.concurrent.thread;

import java.util.concurrent.ThreadFactory;

/**
 * Created by liang.he on 16/1/12.
 * 知道有这个接口就行了，然而我感觉还是没有什么卵用
 */
public class TestThreadFactory implements ThreadFactory{

	public void testMain() {

	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);
		return thread;
	}
}
