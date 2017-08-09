package com.evinance.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import com.evinance.assignment.Appender;
import com.evinance.assignment.MessageProcessor;

/**
 * Implements the queue to process the log messages
 * @author arjunkarnwal
 *
 */
public class LoggingQueueDispatcher implements QueueDispatcher{
	private LinkedBlockingQueue<LogMessage> pendingMessages;
	private Iterable<Appender> listeners;
	private ThreadAdapter threadAdapter;
	private final MessageProcessor messageProcessor;
	private ExecutorService executor =null;

	public LoggingQueueDispatcher(LinkedBlockingQueue<LogMessage> pendingMessages, Iterable<Appender> listeners, ThreadAdapter threadAdapter, MessageProcessor messageProcessor)
	{
		this.pendingMessages = pendingMessages;
		this.listeners = listeners;
		this.threadAdapter = threadAdapter;
		this.messageProcessor = messageProcessor;
	}

	@Override
	public void start()
	{
		//  Using interface  'threadAdapter.createBackgroundThread' to allow unit testing
		executor = threadAdapter.createBackGroundThread(this::messageLoop);
	}
	
	@Override
	public void waitForCompletion()
	{
		executor.shutdownNow();
	    synchronized(pendingMessages) {
			while(!pendingMessages.isEmpty()) {
				LogMessage logMessage =  pendingMessages.remove();
				for(Appender appender : this.listeners) {
					appender.append(messageProcessor.process(logMessage.getMessage(), logMessage.getTimestamp(), logMessage.getImportance(), logMessage.getSource(), logMessage.getThreadId()));
				}
			}
		}
	}

	private void messageLoop()
	{
		LogMessage logMessage =  null;
		synchronized(pendingMessages) {
			try {
				while((logMessage = pendingMessages.take())!=null)
				{
					// !!!!! Now it is safe to use append without ever using lock or forcing important threads to wait.
					for(Appender appender : this.listeners) {
						appender.append(messageProcessor.process(logMessage.getMessage(), logMessage.getTimestamp(), logMessage.getImportance(), logMessage.getSource(), logMessage.getThreadId()));
					}
				}
			} catch (InterruptedException e) {
			}
		}
	}
}
