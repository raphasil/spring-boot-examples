package com.raphasil.example.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * Created by raphael on 29/07/17.
 */
@Component
@Validated
@ConfigurationProperties(prefix = "application")
public class ApplicationProperty {

  @Valid
  private AsyncProperty async;

  @Valid
  private ClientsProperty clients;

  @Valid
  private SchedulingProperty scheduling;

  public AsyncProperty getAsync() {
    return async;
  }

  public void setAsync(AsyncProperty async) {
    this.async = async;
  }

  public ClientsProperty getClients() {
    return clients;
  }

  public void setClients(ClientsProperty clients) {
    this.clients = clients;
  }

  public SchedulingProperty getScheduling() {
    return scheduling;
  }

  public void setScheduling(SchedulingProperty scheduling) {
    this.scheduling = scheduling;
  }
}
