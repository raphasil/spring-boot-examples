package com.raphasil.example.async.integration;

import com.raphasil.example.async.Application;
import com.raphasil.example.async.configuration.AsyncConfiguration;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void test() {
    final ResponseEntity<String> result = restTemplate.getForEntity("/api/v1/users/sync", String.class);
    Assert.assertThat(result.getStatusCode(), Matchers.is(HttpStatus.OK));
  }


}
