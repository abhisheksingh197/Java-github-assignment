package com.hashedin.task;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import com.hashedin.javatemplate.BaseUnitTest;

@ContextConfiguration(classes = TaskQueueTest.class)
public class TaskQueueTest extends BaseUnitTest {

	@Autowired
	private AdderTask adder;
	
	@Autowired
	private TaskQueue queue;

	@Bean
	public TaskQueue getTaskQueue() {
		return new SimpleTaskQueue();
	}
	
	@Test
	public void testEnqueue() {
		assertNotNull(adder);
		assertNotNull(queue);
		
		queue.enqueue(AdderTask.class, Arrays.asList(10, 20, 12));
		assertEquals(adder.getSum(), 42);
	}

}

@Service
class AdderTask implements Task<List<Integer>> {

	private int sum = 0;
	
	@Override
	public void execute(List<Integer> numbers) {
		for(Integer num : numbers) {
			sum += num;
		}
	}
	
	int getSum() {
		return sum;
	}
}