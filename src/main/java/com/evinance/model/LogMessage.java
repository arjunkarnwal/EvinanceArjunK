package com.evinance.model;

import java.util.Date;

/**
 * Model class for Log Messages
 * @author arjunkarnwal
 *
 */
public class LogMessage {

	public Date timestamp;
	public LoggingLevel importance;
	public String message;
	public long threadId;
	public Object source;
	
	private LogMessage(String message, Date timestamp, LoggingLevel importance, Object source, long threadId)
    {
        this.timestamp = timestamp;
        this.message = message;
        this.source = source;
        this.threadId = threadId;
        this.importance = importance;
    }
	
	public static LogMessage create(String message, Date timestamp, LoggingLevel importance, String source, long threadId)
    {
        return  new LogMessage(message, timestamp, importance, source, threadId);
    }
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public LoggingLevel getImportance() {
		return importance;
	}

	public void setImportance(LoggingLevel importance) {
		this.importance = importance;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getThreadId() {
		return threadId;
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}
	
}
