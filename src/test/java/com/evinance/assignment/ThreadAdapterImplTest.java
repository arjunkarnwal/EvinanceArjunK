package com.evinance.assignment;

import java.util.concurrent.ExecutorService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.evinance.model.ThreadAdapter;
import com.evinance.model.ThreadAdapterImpl;

@RunWith(MockitoJUnitRunner.class)
public class ThreadAdapterImplTest {
	
	ThreadAdapter threadAdapter;
	
	
	@Before
	public void setUp() throws Exception {
		threadAdapter =  new ThreadAdapterImpl();
	}
	  
	@Test
	public void testGetCurrentThreadId() {
	}
	
	@Test
	public void testCreateBackGroundThread() {
		ExecutorService executorService = Mockito.spy(threadAdapter.createBackGroundThread(this::testGetCurrentThreadId));
		Assert.assertNotNull(executorService);
		executorService.shutdown();
	}

}
