package com.evinance.model;

import java.util.Date;

import com.evinance.assignment.MessageProcessor;

/**
 * Class to process the log message
 * @author arjunkarnwal
 *
 */
public class LogMessageProcessor implements MessageProcessor {
	
	@Override
	public String process(String message, Object... params) {
		
		return String.format(" %s  %tD %s %s", ((LoggingLevel)params[1]).toString(), (Date)params[0], (String)params[2], message, Long.toString((Long)params[3]));
	}

}
