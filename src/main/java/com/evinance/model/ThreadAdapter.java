package com.evinance.model;

public interface ThreadAdapter {
	long getCurrentThreadId();
	Thread createBackGroundThread(Runnable target);
}
