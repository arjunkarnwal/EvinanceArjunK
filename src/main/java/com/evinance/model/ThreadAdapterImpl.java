package com.evinance.model;

public class ThreadAdapterImpl implements ThreadAdapter{

	public long getCurrentThreadId()
    {
        return Thread.currentThread().getId();
    }
}
