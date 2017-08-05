package com.evinance.assignment;

/**
 * An interface for appending logged messages to their final destination. Implementations
 * of this class might output messages to files or messaging systems.
 */
public interface Appender {

    void append(String entry);
}
