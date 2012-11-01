package org.societies.android.sync.box;

/**
 * Base class of Box operations.
 * 
 * @author Kato
 */
public abstract class BoxOperation extends Thread {
	
	/**
	 * Cancels the operation.
	 */
	public abstract void cancel();
	
	@Override
	public abstract void run();
}
