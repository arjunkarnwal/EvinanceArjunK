package com.evinance.assignment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.evinance.model.LogMessage;
import com.evinance.model.LoggingLevel;
import com.evinance.model.QueueDispatcher;
import com.evinance.model.ThreadAdapter;
import com.evinance.model.ThreadAdapterImpl;

import org.junit.Assert;
import java.util.concurrent.LinkedBlockingQueue;

@RunWith(MockitoJUnitRunner.class)
public class AsyncLoggerTest {

    private AsyncLogger logger;
    private LinkedBlockingQueue<LogMessage> pendingMessages;
    private String loggerFor;
    private ThreadAdapter threadAdapter;
    @Mock
    private QueueDispatcher loggingQueueDispatcher;



    @Before
    public void setUp() throws Exception {
    		pendingMessages = new LinkedBlockingQueue<LogMessage>();
    		loggerFor = "MyLogger";
    		threadAdapter = new ThreadAdapterImpl();
    		logger = new AsyncLogger(pendingMessages, loggerFor, threadAdapter, loggingQueueDispatcher);
    }

    @Test
    public void testLog() throws Exception {
        for (int i=0; i<100; i++) {
        		logger.log("Test message " + i,LoggingLevel.Debug);
        }
        int i=0;
        while(!pendingMessages.isEmpty()) {
			LogMessage logMessage =  pendingMessages.remove();
			Assert.assertEquals("Test message " + i, logMessage.getMessage());
			i++;
        }
        Assert.assertEquals(100, i);
        
    }

}