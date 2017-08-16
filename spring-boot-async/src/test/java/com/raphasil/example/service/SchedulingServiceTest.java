package com.raphasil.example.service;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SchedulingServiceTest {

  private SchedulingService schedulingService;

  @Before
  public void setUp() throws Exception {
    schedulingService = new SchedulingService();
  }

  @Test
  public void getLastUpdateDate() throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    schedulingService.schedule();
    final String result = schedulingService.getLastUpdateDate();
    Assert.assertThat(result, Matchers.containsString(formatter.format(LocalDateTime.now())));
  }

}