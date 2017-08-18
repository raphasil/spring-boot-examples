package com.raphasil.example.async.configuration;

import com.raphasil.example.async.configuration.interceptor.CustomInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by raphael on 30/07/17.
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(WebMvcConfiguration.class);

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new CustomInterceptor()).addPathPatterns("/**");
  }

}
