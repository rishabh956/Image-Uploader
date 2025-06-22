package com.imageuploader.amazon.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration {
	
	@Bean("asyncTaskExecutor")
	public Executor asyncTaskExecutor()
	{
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor() ;
		taskExecutor.setCorePoolSize(4); // 4 threads are still alove if they are idal
		taskExecutor.setQueueCapacity(150); // queue can handle max 150 taks post that new thread has been created up to the max pool size that we define
		taskExecutor.setMaxPoolSize(4);
		taskExecutor.setThreadNamePrefix("AsyncTaskThread--");
		taskExecutor.initialize();
		return taskExecutor ;
		
	}

}
