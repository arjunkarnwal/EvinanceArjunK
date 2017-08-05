package com.evinance.assignment;

import java.text.MessageFormat;
import java.util.Random;

public class DelayingMessageProcessor implements MessageProcessor {

    private Random random = new Random();


    public String process(String message, Object... params) {
        try {
            Thread.sleep(random.nextInt(50));
        } catch (InterruptedException e) {}

        return MessageFormat.format(message, params);
    }
}
