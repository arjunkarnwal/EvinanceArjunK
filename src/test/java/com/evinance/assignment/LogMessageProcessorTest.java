package com.evinance.assignment;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.evinance.model.LogMessageProcessor;
import com.evinance.model.LoggingLevel;

public class LogMessageProcessorTest {
	
	MessageProcessor messageProcessor;
	public static final String EXPECTED_PROCESSED_STRING = "Info  08/08/17 MyLogger Test 1";
	
	@Before
    public void setUp() {
		messageProcessor = new LogMessageProcessor();
	}
	
	@Test
	public void testProcess() {
		LocalDate localDate = LocalDate.of( 2017 , Month.AUGUST , 8 ); 
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		messageProcessor.process("Test", date, LoggingLevel.Info, "MyLogger", Long.parseLong("1"));
	}
}
