package com.evinance.assignment;

/**
 * This class is used to convert a logged message to a final string message that will
 * be appended to an appender. This is useful in cases where logged messages need to
 * be transformed before logging. It's totally possible that this transformation is
 * time consuming.
 */
public interface MessageProcessor {
    String process(String message, Object... params);
}
