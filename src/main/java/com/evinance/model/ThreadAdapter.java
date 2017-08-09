package com.evinance.model;

import java.util.concurrent.ExecutorService;
import java.util.function.Function;

public interface ThreadAdapter {
	long getCurrentThreadId();
	ExecutorService createBackGroundThread(Runnable function);
}
