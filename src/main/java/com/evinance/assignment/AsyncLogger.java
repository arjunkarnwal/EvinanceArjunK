package com.evinance.assignment;

public class AsyncLogger implements Logger {

    private final Appender appender;
    private final MessageProcessor messageProcessor;


    public AsyncLogger(Appender appender, MessageProcessor messageProcessor) {
        this.appender = appender;
        this.messageProcessor = messageProcessor;
    }

    public void log(String message, Object... params) {
    }

    public void shutdown() {
    }
}
