package com.github.dunno.concurrent.sync;

import org.junit.Test;

/**
 * Created by liang.he on 16/1/16.
 */
public class TestSync {
	//类锁：在代码中的方法上加了static和synchronized的锁，或者synchronized(xxx.class）的代码段
	//对象锁：在代码中的方法上加了synchronized的锁，或者synchronized(this）的代码段
	//私有锁：在类内部声明一个私有属性如private Object lock，在需要加锁的代码段synchronized(lock）

	private Integer lock = new Integer(1);

	private Integer lock2 = new Integer(2);

	private final static Integer staticLock = new Integer(3);

	//无锁
	public void noLockMethod() {
		long start = System.currentTimeMillis();
		System.out.println("noLockMethod start! start:" + start);
		int sleep = 2;
		while (sleep > 0) {
			try {
				Thread.sleep(1000);
				System.out.println("noLockMethod-lock:" + lock2);
				System.out.println("noLockMethod-lock:" + lock);
				System.out.println("noLockMethod-lock:" + staticLock);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sleep --;
		}
		long end = System.currentTimeMillis();
		System.out.println("noLockMethod end! end:" + end + " cost:" + (end - start));
	}

	//对象锁1
	public synchronized void objectLockMethod() {
		long start = System.currentTimeMillis();
		System.out.println("objectLockMethod start! start:" + start);
		int sleep = 2;
		while (sleep > 0) {
			try {
				Thread.sleep(1000);
				System.out.println("objectLockMethod-lock:" + lock2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sleep --;
		}
		long end = System.currentTimeMillis();
		System.out.println("objectLockMethod end! end:" + end + " cost:" + (end - start));
	}

	//对象锁2
	public void objectLockMethod2() {
		synchronized (this) {
			long start = System.currentTimeMillis();
			System.out.println("objectLockMehtod2 start! start:" + start);
			int sleep = 2;
			while (sleep > 0) {
				try {
					Thread.sleep(1000);
					System.out.println("objectLockMehtod2-lock:" + lock2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sleep --;
			}
			long end = System.currentTimeMillis();
			System.out.println("objectLockMehtod2 end! end:" + end + " cost:" + (end - start));
		}
	}

	//私有锁 锁变量1
	public void privateLockMethod() {
		synchronized (lock) {
			long start = System.currentTimeMillis();
			System.out.println("privateLockMethod start! start:" + start);
			int sleep = 2;
			while (sleep > 0) {
				try {
					Thread.sleep(1000);
					System.out.println("privateLockMethod1-lock:" + lock2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sleep --;
			}
			long end = System.currentTimeMillis();
			System.out.println("privateLockMethod end! end:" + end + " cost:" + (end - start));
		}
	}

	//私有锁 锁变量2
	public void privateLockMethod2() {
		synchronized (lock2) {
			long start = System.currentTimeMillis();
			System.out.println("privateLockMethod2 start! mills:" + start);
			int sleep = 2;
			while (sleep > 0) {
				try {
					Thread.sleep(1000);
					System.out.println("privateLockMethod2-lock:" + lock);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sleep --;
			}
			long end = System.currentTimeMillis();
			System.out.println("privateLockMethod end! end:" + end + " cost:" + (end - start));
		}
	}

	//类锁
	public synchronized static void classLockMethod() {
		long start = System.currentTimeMillis();
		System.out.println("classLockMethod start! mills:" + start);
		int sleep = 2;
		while (sleep > 0) {
			try {
				Thread.sleep(1000);
				System.out.println("classLockMethod-lock:" + staticLock);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sleep --;
		}
		long end = System.currentTimeMillis();
		System.out.println("classLockMethod end! end:" + end + " cost:" + (end - start));
	}

	//类锁
	public static void classLockMethod2() {
		synchronized (TestSync.class) {
			long start = System.currentTimeMillis();
			System.out.println("classLockMethod2 start! mills:" + start);
			int sleep = 2;
			while (sleep > 0) {
				try {
					Thread.sleep(1000);
					System.out.println("classLockMethod2-lock:" + staticLock);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sleep --;
			}
			long end = System.currentTimeMillis();
			System.out.println("classLockMethod2 end! end:" + end + " cost:" + (end - start));
		}
	}

	//测试私有锁锁不同变量是否会有竞争
	//结论:
	//只打开注释1,注释2,可以说明“当私有锁监视同一资源会竞争,等待其中一个资源释放,当监视的资源不是同一个不会产生竞争”
	//只打开注释3,注释4,可以说明“对象锁会互相竞争，等待另一个方法结束”
	//只打开注释5,注释6,可以说明"类锁会互相竞争，等待另一个释放资源"
	//只打开注释1,注释6,打开注释1,注释5,可以说明"不同的锁不会竞争"
	//只打开注释1,注释7,3-7或者5-7,可以说明"无锁变量不会和锁变量竞争"
	@Test
	public void test1() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Thread thread1 = new Thread(new Runnable() {
					@Override
					public void run() {

						//privateLockMethod();//注释1
						//objectLockMethod();//注释3
						classLockMethod();//注释5
					}
				});
				Thread thread2 = new Thread(new Runnable() {
					@Override
					public void run() {

						//privateLockMethod2();//注释2
						//objectLockMethod2();//注释4
						//classLockMethod2();//注释6
						noLockMethod();//注释7
					}
				});
				thread1.start();
				thread2.start();

				try {
					thread1.join();
					thread2.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
