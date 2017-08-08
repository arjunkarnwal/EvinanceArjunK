package com.evinance.assignment;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import com.evinance.model.LogMessage;
import com.evinance.model.LoggingLevel;
import com.evinance.model.LoggingQueueDispatcher;
import com.evinance.model.ThreadAdapter;



public class AsyncLogger implements Logger {

	private LinkedBlockingQueue<LogMessage> pendingMessages;
	private String loggerFor;
    private ThreadAdapter threadAdapter;
    private LoggingQueueDispatcher loggingQueueDispatcher;

    public AsyncLogger(LinkedBlockingQueue<LogMessage> pendingMessages, String loggerFor, ThreadAdapter threadAdapter) {
        this.pendingMessages = pendingMessages;
        this.loggerFor = loggerFor;
        this.threadAdapter = threadAdapter;
    }
    
    @Override
    public void log(String message, Object... params) {
    		Date timestamp = new Date();
        long threadId = this.threadAdapter.getCurrentThreadId();
        //adds message to the queue in lock-free manner and immediately returns control to caller
        pendingMessages.add(LogMessage.create(message,timestamp, (LoggingLevel)params[0], loggerFor,threadId));
    }
    

    @Override
    public void shutdown() {
    		this.loggingQueueDispatcher.waitForCompletion();
    }
    
}
