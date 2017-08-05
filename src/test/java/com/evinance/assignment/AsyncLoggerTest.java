package com.evinance.assignment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class AsyncLoggerTest {

    private AsyncLogger fixture;

    @Mock
    private Appender appender;


    @Before
    public void setUp() throws Exception {
        fixture = new AsyncLogger(appender, new DelayingMessageProcessor());
    }

    @Test
    public void log() throws Exception {
        InOrder inOrder = inOrder(appender);

        for (int i=0; i<100; i++) {
            fixture.log("Test message {0}", i);
        }

        fixture.shutdown();

        for (int i=0; i<100; i++) {
            inOrder.verify(appender).append("Test message " + i);
        }
    }

}