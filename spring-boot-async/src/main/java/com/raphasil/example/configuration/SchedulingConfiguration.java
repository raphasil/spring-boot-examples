package com.raphasil.example.configuration;

import com.raphasil.example.property.ApplicationProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.ErrorHandler;

import java.util.concurrent.Executor;

/**
 * Created by raphael on 29/07/17.
 */
@Configuration
@EnableScheduling
public class SchedulingConfiguration implements SchedulingConfigurer {

  private static final Logger LOG = LoggerFactory.getLogger(SchedulingConfiguration.class);

  private final ApplicationProperty properties;

  @Autowired
  public SchedulingConfiguration(ApplicationProperty properties) {
    this.properties = properties;
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    threadPoolTaskScheduler.setThreadNamePrefix(properties.getScheduling().getThreadNamePrefix());
    threadPoolTaskScheduler.setPoolSize(properties.getScheduling().getPoolSize());
    threadPoolTaskScheduler.setErrorHandler(throwable -> LOG.error("Unexpected error occurred invoking scheduling: " + throwable.getMessage(), throwable));
    threadPoolTaskScheduler.initialize();
    scheduledTaskRegistrar.setScheduler(threadPoolTaskScheduler);
  }

}
