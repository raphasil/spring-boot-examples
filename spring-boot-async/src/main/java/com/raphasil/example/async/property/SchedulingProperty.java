package com.raphasil.example.async.property;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * Created by raphael on 29/07/17.
 */
public class SchedulingProperty {

  @NotNull
  @Min(1)
  @Max(Integer.MAX_VALUE)
  private Integer poolSize;

  @NotBlank
  private String threadNamePrefix;

  public Integer getPoolSize() {
    return poolSize;
  }

  public void setPoolSize(Integer poolSize) {
    this.poolSize = poolSize;
  }

  public String getThreadNamePrefix() {
    return threadNamePrefix;
  }

  public void setThreadNamePrefix(String threadNamePrefix) {
    this.threadNamePrefix = threadNamePrefix;
  }
}
