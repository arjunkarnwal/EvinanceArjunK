package com.evinance.model;

import java.util.concurrent.LinkedBlockingQueue;

import com.evinance.assignment.Appender;
import com.evinance.assignment.Logger;
import com.evinance.assignment.MessageProcessor;

public class LoggingQueueDispatcher implements QueueDispatcher{
	private LinkedBlockingQueue<LogMessage> pendingMessages;
    private Iterable<Appender> listeners;
    private ThreadAdapter threadAdapter;
    private Logger logger;
    private Thread dispatcherThread;
    private final MessageProcessor messageProcessor;
    private boolean breakLoop=false;

    public LoggingQueueDispatcher(LinkedBlockingQueue<LogMessage> pendingMessages, Iterable<Appender> listeners, ThreadAdapter threadAdapter, Logger logger, MessageProcessor messageProcessor)
    {
        this.pendingMessages = pendingMessages;
        this.listeners = listeners;
        this.threadAdapter = threadAdapter;
        this.logger = logger;
        this.messageProcessor = messageProcessor;
    }

    public void start()
    {
        //  Here I use 'new' operator, only to simplify example. Should be using interface  'threadAdapter.createBackgroundThread' to allow unit testing
    		Thread thread = new Thread(this::messageLoop);
    		thread.start();
        logger.log("Asked to start log message Dispatcher ",LoggingLevel.Info);
        dispatcherThread = thread;
    }

    public void waitForCompletion()
    {
    		synchronized(pendingMessages) {
			while(!pendingMessages.isEmpty()) {
				LogMessage logMessage =  pendingMessages.remove();
				for(Appender appender : this.listeners) {
					appender.append(messageProcessor.process(logMessage.getMessage(), logMessage.getTimestamp(), logMessage.getImportance(), logMessage.getSource(), logMessage.getThreadId()));
				}
			}
			breakLoop = true;
		}
    }

    private void messageLoop()
    {
        logger.log("Entering dispatcher message loop...",LoggingLevel.Info);
        LogMessage logMessage =  null;
        try {
			while(!breakLoop && (logMessage = pendingMessages.take())!=null)
			{
			    // !!!!! Now it is safe to use append without ever using lock or forcing important threads to wait.
			    for(Appender appender : this.listeners) {
					appender.append(messageProcessor.process(logMessage.getMessage(), logMessage.getTimestamp(), logMessage.getImportance(), logMessage.getSource(), logMessage.getThreadId()));
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

    }
}
