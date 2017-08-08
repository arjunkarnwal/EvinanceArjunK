package com.evinance.model;

public class ThreadAdapterImpl implements ThreadAdapter{

	@Override
	public long getCurrentThreadId()
    {
        return Thread.currentThread().getId();
    }

	@Override
	public Thread createBackGroundThread(Runnable target) {
		Thread thread = new Thread(target);
		return thread;
	}
	
	
}
