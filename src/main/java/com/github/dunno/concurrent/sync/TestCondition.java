package com.github.dunno.concurrent.sync;

import org.junit.Test;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liang.he on 16/1/13.
 */
public class TestCondition{
	//互斥锁（独占锁，可重入）
	private final Lock lock = new ReentrantLock();

	//队列不为空
	private Condition notEmpty = lock.newCondition();

	//队列不满
	private Condition notFull = lock.newCondition();

	class Producer implements Runnable {
		//仓库
		private Queue<Integer> dept;

		//构造函数
		public Producer(Queue<Integer> dept) {
			this.dept = dept;
		}

		public void produce(int a) {
			lock.lock();
			try {
				while (!dept.offer(a)) {
					System.out.println("队列满了!生产不了哦");
					notFull.await();
					notEmpty.signal();
				}
				System.out.println("队列有空闲了!放进去了一个数据;" + a);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}

		}


		@Override
		public void run() {
			while (true) {

				try {
					produce(new Random().nextInt(10));
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class Customer implements Runnable {
		//仓库
		private Queue<Integer> dept;

		//构造函数
		public Customer(Queue<Integer> dept) {
			this.dept = dept;
		}

		public void custome() {
			lock.lock();
			try {
				while(dept.peek() == null) {
					System.out.println("队列空了!消费不了哦");
					notEmpty.await();
					notFull.signal();
				}
				Integer poll = dept.poll();
				System.out.println("队列有物品了!消费了一个数据;" + poll);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		@Override
		public void run() {
			while (true) {

				try {
					custome();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	@Test
	public void testMain() {
		Queue<Integer> dept = new ArrayBlockingQueue<Integer>(3);
		dept.offer(9);
		dept.offer(8);
		dept.offer(7);
		Thread producer = new Thread(new Producer(dept));
		Thread customer = new Thread(new Customer(dept));

		producer.start();
		customer.start();

		try {
			producer.join();
			customer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
