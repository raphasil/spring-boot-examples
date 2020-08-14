package com.raphasil.example.async.property.client;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by raphael on 29/07/17.
 */
public class RandomUserClientProperty {

  @NotBlank
  private String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
