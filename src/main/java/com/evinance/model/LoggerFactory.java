package com.evinance.model;

import com.evinance.assignment.Logger;

public interface LoggerFactory {
	Logger getLogger(String loggerFor);
}
