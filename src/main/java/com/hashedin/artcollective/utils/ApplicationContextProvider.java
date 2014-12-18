package com.hashedin.artcollective.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component

public class ApplicationContextProvider implements ApplicationContextAware {
	
	@edu.umd.cs.findbugs.annotations.SuppressWarnings(
		    value = "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", 
		    justification = "writing to Static field Find Bug Error is suppressed")
	private static ApplicationContext applicationContext = null;
	
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
