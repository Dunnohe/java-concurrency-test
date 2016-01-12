package com.github.dunno.concurrent.sync;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang.he on 16/1/12.
 * 类锁和对象锁不会产生竞争，二者的加锁方法不会相互影响。
 * 私有锁和对象锁也不会产生竞争，二者的加锁方法不会相互影响。
 * synchronized直接加在方法上和synchronized(this)都是对当前对象加锁，二者的加锁方法够成了竞争关系，同一时刻只能有一个方法能执行。
 */
public class TestSynchronized {
	private int method = 0;

	private static int method1 = 0;

	private final Object lock = new Object();

	//common 不会锁住任何变量
	public void add() {
		method = method + 1;
	}

	//对象锁
	public synchronized void add1() {
		method = method + 1;
	}

	//私有锁
	public void add2() {
		synchronized (lock) {
			method = method + 1;
		}
	}

	//对象锁
	public void add3() {
		synchronized (this) {
			method = method + 1;
		}
	}

	//类锁
	public static void add4() {
		synchronized (TestSynchronized.class) {
			method1 = method1 + 1;
		}
	}

	@Before
	public void setUp() {
		method = 0;
		method1 = 0;
	}

	@After
	public void tearDown() {
		method = 0;
		method1 = 0;
	}

	/**
	 * 测试多线程调用add方法，复现共享变量bad case
	 */
	@Test
	public void testAdd() {
		for (int k = 0; k < 10; k++) {
			List<Thread> threadList = new ArrayList<Thread>();
			for (int i = 0; i < 1000; i++) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						for (int j = 0; j < 10; j++) {
							add();
						}
					}
				});
				threadList.add(thread);
			}

			for (Thread thread : threadList) {
				thread.start();
			}

			for (Thread thread : threadList) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			Assert.assertEquals(10000, method);
			setUp();
		}
	}

	/**
	 * 测试多线程调用add1方法，验证sync关键字
	 */
	@Test
	public void testAdd1() {
		for (int k = 0; k < 10; k++) {
			List<Thread> threadList = new ArrayList<Thread>();
			for (int i = 0; i < 1000; i++) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						for (int j = 0; j < 10; j++) {
							//success
							add1();
							//fail
							//new TestSynchronized().add1();
						}
					}
				});
				threadList.add(thread);
			}

			for (Thread thread : threadList) {
				thread.start();
			}

			for (Thread thread : threadList) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			Assert.assertEquals(10000, method);
			setUp();
		}
	}

	@Test
	public void testAdd2() {
		for (int k = 0; k < 10; k++) {
			List<Thread> threadList = new ArrayList<Thread>();
			for (int i = 0; i < 1000; i++) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						for (int j = 0; j < 10; j++) {
							//success
							add2();
							//fail
							//new TestSynchronized().add2();
						}
					}
				});
				threadList.add(thread);
			}

			for (Thread thread : threadList) {
				thread.start();
			}

			for (Thread thread : threadList) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			Assert.assertEquals(10000, method);
			setUp();
		}
	}

	@Test
	public void testAdd3() {
		for (int k = 0; k < 10; k++) {
			List<Thread> threadList = new ArrayList<Thread>();
			for (int i = 0; i < 1000; i++) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						for (int j = 0; j < 10; j++) {
							//success
							add3();
							//fail
							//new TestSynchronized().add3();
						}
					}
				});
				threadList.add(thread);
			}

			for (Thread thread : threadList) {
				thread.start();
			}

			for (Thread thread : threadList) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			Assert.assertEquals(10000, method);
			setUp();
		}
	}

	@Test
	public void testAdd4() {
		for (int k = 0; k < 10; k++) {
			List<Thread> threadList = new ArrayList<Thread>();
			for (int i = 0; i < 1000; i++) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						for (int j = 0; j < 10; j++) {
							//success
							add4();
							//success
							//new TestSynchronized().add4();
						}
					}
				});
				threadList.add(thread);
			}

			for (Thread thread : threadList) {
				thread.start();
			}

			for (Thread thread : threadList) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			Assert.assertEquals(10000, method1);
			setUp();
		}
	}


}
