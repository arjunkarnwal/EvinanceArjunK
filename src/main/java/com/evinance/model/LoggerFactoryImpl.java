package com.evinance.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.evinance.assignment.Appender;
import com.evinance.assignment.AsyncLogger;
import com.evinance.assignment.Logger;
import com.evinance.assignment.MessageProcessor;

public class LoggerFactoryImpl implements LoggerFactory {
	private LinkedBlockingQueue<LogMessage> pendingMessages;
    private ThreadAdapter threadAdapter;
  
    
    private ConcurrentHashMap<String, Logger> loggersCache = new ConcurrentHashMap<String, Logger>();


    public LoggerFactoryImpl(LinkedBlockingQueue<LogMessage> pendingMessages, ThreadAdapter threadAdapter)
    {
        this.pendingMessages = pendingMessages;
        this.threadAdapter = threadAdapter;
    }

    public Logger getLogger(String loggerFor)
    {
    		if(!loggersCache.contains(loggerFor)) {
    			loggersCache.put(loggerFor, new AsyncLogger(pendingMessages, loggerFor, threadAdapter));
    		}
    		return loggersCache.get(loggerFor);
    }
}
