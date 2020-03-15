package com.boe.admin.uiadmin.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event)  {
		
		
			
			log.info("============== {} ==============", "Application start success: running ....");
			
		
		
	}

	
}