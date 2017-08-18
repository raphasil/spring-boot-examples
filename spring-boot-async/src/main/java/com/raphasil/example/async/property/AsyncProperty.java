package com.raphasil.example.async.property;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * Created by raphael on 29/07/17.
 */
public class AsyncProperty {

  @NotNull
  @Min(1)
  @Max(Integer.MAX_VALUE)
  private Integer corePoolSize;

  @NotNull
  @Min(1)
  @Max(Integer.MAX_VALUE)
  private Integer maxPoolSize;

  @NotNull
  @Min(1)
  @Max(Integer.MAX_VALUE)
  private Integer queueCapacity;

  @NotBlank
  private String threadNamePrefix;

  public Integer getCorePoolSize() {
    return corePoolSize;
  }

  public void setCorePoolSize(Integer corePoolSize) {
    this.corePoolSize = corePoolSize;
  }

  public Integer getMaxPoolSize() {
    return maxPoolSize;
  }

  public void setMaxPoolSize(Integer maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
  }

  public Integer getQueueCapacity() {
    return queueCapacity;
  }

  public void setQueueCapacity(Integer queueCapacity) {
    this.queueCapacity = queueCapacity;
  }

  public String getThreadNamePrefix() {
    return threadNamePrefix;
  }

  public void setThreadNamePrefix(String threadNamePrefix) {
    this.threadNamePrefix = threadNamePrefix;
  }
}
