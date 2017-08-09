package com.evinance.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class for thread related functions
 * @author arjunkarnwal
 *
 */
public class ThreadAdapterImpl implements ThreadAdapter{

	@Override
	public long getCurrentThreadId()
    {
        return Thread.currentThread().getId();
    }

	@Override
	public ExecutorService createBackGroundThread(Runnable function) {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new Runnable() {
			@Override
	        public void run() {
				function.run();
	        }
	    });
		return executor;
	}
	
	
}
