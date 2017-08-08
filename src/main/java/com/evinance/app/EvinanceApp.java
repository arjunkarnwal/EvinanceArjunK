package com.evinance.app;

import java.io.Console;
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

public class EvinanceApp {

	public static void main(String[] args) {
		LinkedBlockingQueue<LogMessage> pendingLogQueue = new LinkedBlockingQueue<LogMessage>();


		ThreadAdapter threadAdapter = new ThreadAdapterImpl();
		LoggerFactory loggerFactory = new LoggerFactoryImpl(pendingLogQueue, threadAdapter);


       // var fileSystem = new FileSystem();
       // var userRoamingPath = GetUserDataDirectory(fileSystem);

        Appender simpleTextFileLogger = new SimpleTextFileAppender("log.txt");
        //simpleTextFileLogger.start();
        Appender consoleListener = new ConsoleAppender();
        ArrayList<Appender> listeners = new ArrayList<Appender>();
        listeners.add(simpleTextFileLogger);
        listeners.add(consoleListener);
        
        MessageProcessor messageProcessor = new LogMessageProcessor();

        QueueDispatcher loggingQueueDispatcher = new LoggingQueueDispatcher(pendingLogQueue, listeners, threadAdapter, loggerFactory.getLogger("MyLogger"),messageProcessor);
        loggingQueueDispatcher.start();

        Logger logger = loggerFactory.getLogger("MyLogger");

        logger.log("you have entered: ",LoggingLevel.Info);
        System.out.println("[Program] pending LogQueue will be stopped now...");
        
        loggingQueueDispatcher.waitForCompletion();
	}

}
