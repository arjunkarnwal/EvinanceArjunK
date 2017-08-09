package com.evinance.model;

import com.evinance.assignment.Logger;

public interface LoggerFactory {
	/**
     * Factory method to return the logger
     */
	Logger getLogger(String loggerFor);
}
