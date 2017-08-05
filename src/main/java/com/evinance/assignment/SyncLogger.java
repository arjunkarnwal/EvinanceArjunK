package com.evinance.assignment;

public class SyncLogger implements Logger {

    private final Appender appender;
    private final MessageProcessor messageProcessor;

    public SyncLogger(Appender appender, MessageProcessor messageProcessor) {
        this.appender = appender;
        this.messageProcessor = messageProcessor;
    }

    public void log(String message, Object... params) {
        appender.append(messageProcessor.process(message, params));
    }

    public void shutdown() {
    }
}
