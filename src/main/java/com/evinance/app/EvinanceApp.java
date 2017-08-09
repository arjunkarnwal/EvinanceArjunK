package com.evinance.app;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import com.evinance.assignment.Appender;
import com.evinance.assignment.Logger;
import com.evinance.assignment.MessageProcessor;
import com.evinance.model.ConsoleAppender;
import com.evinance.model.LogMessage;
import com.evinance.model.LogMessageProcessor;
import com.evinance.model.LoggerFactory;
import com.evinance.model.LoggerFactoryImpl;
import com.evinance.model.LoggingLevel;
import com.evinance.model.LoggingQueueDispatcher;
import com.evinance.model.QueueDispatcher;
import com.evinance.model.SimpleTextFileAppender;
import com.evinance.model.ThreadAdapter;
import com.evinance.model.ThreadAdapterImpl;

/**
 * Class to test End to End Flow
 * @author arjunkarnwal
 *
 */
public class EvinanceApp {
	
	public static void main(String[] args) {
		LinkedBlockingQueue<LogMessage> pendingLogQueue = new LinkedBlockingQueue<LogMessage>();

		ThreadAdapter threadAdapter = new ThreadAdapterImpl();

        Appender simpleTextFileLogger = new SimpleTextFileAppender("log.txt");
        Appender consoleListener = new ConsoleAppender();
        ArrayList<Appender> listeners = new ArrayList<Appender>();
        listeners.add(simpleTextFileLogger);
        listeners.add(consoleListener);
        
        MessageProcessor messageProcessor = new LogMessageProcessor();

        QueueDispatcher loggingQueueDispatcher = new LoggingQueueDispatcher(pendingLogQueue, listeners, threadAdapter, messageProcessor);
        loggingQueueDispatcher.start();
		LoggerFactory loggerFactory = new LoggerFactoryImpl(pendingLogQueue, threadAdapter, loggingQueueDispatcher);

        Logger logger = loggerFactory.getLogger("MyLogger");

        logger.log("you have entered: 1",LoggingLevel.Info);
        logger.log("you have entered: 2",LoggingLevel.Info);
        logger.log("you have entered: 3",LoggingLevel.Info);
        logger.log("you have entered: 4",LoggingLevel.Info);
        logger.log("you have entered: 5",LoggingLevel.Info);
        logger.log("you have entered: 6",LoggingLevel.Info);
        
        System.out.println("[Program] pending LogQueue will be stopped now...");
        
        logger.shutdown();
	}

}
