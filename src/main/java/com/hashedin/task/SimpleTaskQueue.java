package com.hashedin.task;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SimpleTaskQueue implements TaskQueue, ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	@Override
	public <P, T extends Task<P>> 
				void enqueue(Class<T> clazz, P parameter) {
		
		T task = applicationContext.getBean(clazz);
		task.execute(parameter);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
