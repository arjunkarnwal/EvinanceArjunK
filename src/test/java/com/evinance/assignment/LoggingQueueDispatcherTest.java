package com.evinance.assignment;

import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.evinance.model.LogMessage;
import com.evinance.model.LoggingQueueDispatcher;
import com.evinance.model.ThreadAdapter;

@RunWith(MockitoJUnitRunner.class)
public class LoggingQueueDispatcherTest {
	
	LoggingQueueDispatcher loggingQueueDispatcher;
	
	private LinkedBlockingQueue<LogMessage> pendingMessages;
	
	@Mock
	private Iterable<Appender> listeners;
	
	@Mock
	private ThreadAdapter threadAdapter;
	
	@Mock
	private MessageProcessor messageProcessor;
	
	@Before
    public void setUp() {
		pendingMessages = new LinkedBlockingQueue<LogMessage>();
		loggingQueueDispatcher = new LoggingQueueDispatcher(pendingMessages, listeners, threadAdapter, messageProcessor);
	}
	
	@Test
    public void testStart() {
		loggingQueueDispatcher.start();
		Mockito.verify(threadAdapter,Mockito.times(1)).createBackGroundThread(Mockito.any(Runnable.class));
	}

}
