/**
 * Copyright 2012 UbiCollab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.societies.android.util;

import java.util.LinkedList;

import android.util.Log;

/**
 * A class to structure threads by running them in pools.
 * 
 * @author Kato
 */
public class ThreadPool {
	
	private static final String TAG = "ThreadPool";
	
	private final int mPoolSize;
	private final LinkedList<Thread> mQueue;
	private final Worker[] mWorkers;
	private boolean mIsWorking;
	private boolean mIsStopping;
	
	/**
	 * Initializes a new thread pool.
	 * @param poolSize The size of the thread pool. The minimum size is 1.
	 */
	public ThreadPool(int poolSize) {
		mPoolSize = Math.max(poolSize, 1);
		mQueue = new LinkedList<Thread>();
		mWorkers = new Worker[mPoolSize];
		mIsWorking = false;
		mIsStopping = false;
	}
	
	/**
	 * Starts the execution of threads.
	 */
	public void startExecution() {
		if (!mIsWorking) {
			Log.i(TAG, "Starting Execution...");
			for (int i = 0; i < mPoolSize; i++) {
				mWorkers[i] = new Worker();
				mWorkers[i].start();
			}
			
			mIsWorking = true;
		}
	}
	
	/**
	 * Stops the execution of threads. If not forced, this method will
	 * block until all threads in the queue has finished executing.
	 * @param forced Whether or not currently running threads should be
	 * forcefully terminated.
	 * @throws InterruptedException If interrupted while waiting for workers
	 * to terminate.
	 */
	public void stopExecution(boolean forced) throws InterruptedException {
		Log.i(TAG, "Stopping execution (Forced: " + forced + ")...");
		
		mIsStopping = true;
		
		synchronized (mQueue) {
			mQueue.notifyAll();
		}
		
		if (forced) {
			synchronized (mQueue) {
				mQueue.clear();
			}
			
			for (Worker worker : mWorkers)
				worker.interrupt();
		}
		
		Log.i(TAG, "Waiting for workers to complete...");
		boolean canStop;
		do {
			canStop = true;
			
			for (Worker worker : mWorkers) {
				if ((worker.isAlive() && !worker.isIdle()) || !mQueue.isEmpty()) {
					canStop = false;
					break;
				}
			}
			
			if (!canStop)
				Thread.sleep(100);
		} while (!canStop);
		
		mIsWorking = false;
		mIsStopping = false;
	}
	
	/**
	 * Adds a thread to the pool.
	 * @param thread The thread to execute.
	 */
	public void add(Thread thread) {
		if (!mIsStopping) {
			Log.i(TAG, "Adding thread to queue...");
			synchronized (mQueue) {
				mQueue.addLast(thread);
				mQueue.notify();
			}
		}
	}
	
	/**
	 * A thread pool worker that executes threads in the queue.
	 * 
	 * @author Kato
	 */
	private class Worker extends Thread {
		
		private static final String TAG = "ThreadPool#Worker";
		
		private Thread mCurrentThread;
		private boolean mIsIdle = true;
		
		@Override
		public void run() {
			
			while (!isInterrupted()) {
				try {
					Log.i(TAG, "Waiting for notification...");
					synchronized (mQueue) {
						while (mQueue.isEmpty()) {
							mQueue.wait();
						}
						
						if (mIsStopping && mQueue.isEmpty())
							break;
						
						mIsIdle = false;
						mCurrentThread = mQueue.removeFirst();
					}
					
					Log.i(TAG, "Running thread...");
					try {
						mCurrentThread.run();
						mIsIdle = true;
					} catch (Exception e) {
						Log.e(TAG, e.getMessage(), e);
					}
				} catch (InterruptedException e) { /* IGNORED */ }
			}
			
			Log.i(TAG, "Worker complete!");
		}
		
		@Override
		public void interrupt() {
			Log.i(TAG, "Worker interrupted!");
			if (mCurrentThread != null && mCurrentThread.isAlive())
				mCurrentThread.interrupt();
			
			super.interrupt();
		}
		
		/**
		 * Checks whether the worker is idle.
		 * @return Whether or not the worker is idle.
		 */
		public boolean isIdle() {
			return mIsIdle;
		}
	}
}
