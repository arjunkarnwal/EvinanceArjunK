package com.evinance.assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

import com.evinance.model.SimpleTextFileAppender;

import org.junit.Assert;

public class SimpleTextFileAppenderTest {
	
	SimpleTextFileAppender simpleTextFileAppender;
	public static final String EXPECTED_FILE_CONTENT = "My String Test";
	
	@Before
    public void setUp() {
		simpleTextFileAppender = new SimpleTextFileAppender("MyLogTest");
	}
	
	@Test
	public void testAppend() {
		simpleTextFileAppender.append(EXPECTED_FILE_CONTENT);
		simpleTextFileAppender.stop();
		FileInputStream fileStream = null;
		try {
			fileStream = new FileInputStream(new File("MyLogTest"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(EXPECTED_FILE_CONTENT, line);
	}
	

}
