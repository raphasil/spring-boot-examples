package com.raphasil.example.feign;

import com.raphasil.example.feign.vendors.RandomUserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by raphael on 29/07/17.
 */
@EnableFeignClients
@SpringBootApplication
public class Application implements CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  private final RandomUserClient randomUserClient;

  public Application(RandomUserClient randomUserClient) {
    this.randomUserClient = randomUserClient;
  }

  @Override
  public void run(String... strings) throws Exception {
    try {

      final ResponseEntity<String> result = randomUserClient.get();

      LOG.info("response code {} body {}", result.getStatusCode(), result.getBody());

    } catch (Exception ex) {
      LOG.error("request error", ex);
    }

  }

  @PostConstruct
  public void postConstruct() {
    LOG.info("Application started");
  }

  @PreDestroy
  public void preDestroy() {
    LOG.info("Application finishing");
  }

  public static void main(String[] args) {
    final ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class).web(false).run(args);
    final int exit = SpringApplication.exit(context);
    System.exit(exit);
  }
}
