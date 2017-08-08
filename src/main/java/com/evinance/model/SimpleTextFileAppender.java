package com.evinance.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;

import com.evinance.assignment.Appender;

public class SimpleTextFileAppender implements Appender{
	private String logFileName;
	private FileOutputStream fileStream;

	public SimpleTextFileAppender(String logFileName)
	{
		this.logFileName = logFileName;
		try {
			fileStream = new FileOutputStream(new File(logFileName), true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop()
	{
		if (fileStream != null)
		{
			try {
				fileStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void append(String message)
	{
		byte[] bytes = (message.toString() + System.lineSeparator()).getBytes(StandardCharsets.UTF_8); 

		try {
			fileStream.write(bytes, 0, bytes.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

