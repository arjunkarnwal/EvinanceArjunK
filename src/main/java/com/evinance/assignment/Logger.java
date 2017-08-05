package com.evinance.assignment;


public interface Logger {

    /**
     * Logs the given message. Calls to this method are non blocking.
     */
    void log(String message, Object... params);

    /**
     * Prepares the logger for a clean shutdown and ensures all messages are logged.
     * Calls to this method are blocking until all messaged are logged.
     */
    void shutdown();
}
