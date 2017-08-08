package com.evinance.model;

import com.evinance.assignment.Appender;

public class ConsoleAppender implements Appender {
	
	@Override
	public void append(String message)
    {
	   System.out.println(message);
	   
	}
}
