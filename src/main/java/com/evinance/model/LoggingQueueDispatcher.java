package com.evinance.model;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.evinance.assignment.Appender;
import com.evinance.assignment.Logger;
import com.evinance.assignment.MessageProcessor;

public class LoggingQueueDispatcher implements QueueDispatcher{
	private LinkedBlockingQueue<LogMessage> pendingMessages;
	private Iterable<Appender> listeners;
	private ThreadAdapter threadAdapter;
	private Thread dispatcherThread;
	private final MessageProcessor messageProcessor;
	private boolean breakLoop=false;

	public LoggingQueueDispatcher(LinkedBlockingQueue<LogMessage> pendingMessages, Iterable<Appender> listeners, ThreadAdapter threadAdapter, MessageProcessor messageProcessor)
	{
		this.pendingMessages = pendingMessages;
		this.listeners = listeners;
		this.threadAdapter = threadAdapter;
		this.messageProcessor = messageProcessor;
	}

	public void start()
	{
		//  Using interface  'threadAdapter.createBackgroundThread' to allow unit testing
		Thread thread = threadAdapter.createBackGroundThread(this::messageLoop);
		thread.start();
		dispatcherThread = thread;
	}

	public void waitForCompletion()
	{
		breakLoop = true;
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
}
