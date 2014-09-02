package com.hashedin.task;

/**
 * Allows consumers to enqueue background tasks
 * 
 * Usage :
 *
 * @param <P> A parameterized class that serves as input parameters
 * @param <T> A specific task implementation that will perform the computation
 * 
 */
public interface TaskQueue {

	public <P, T extends Task<P>> 
			void enqueue(Class<T> clazz, P parameter);
}
