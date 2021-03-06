package com.raphasil.example.async.property;

import com.raphasil.example.async.property.client.RandomUserClientProperty;

import javax.validation.Valid;

/**
 * Created by raphael on 29/07/17.
 */
public class ClientsProperty {

  @Valid
  private RandomUserClientProperty randomUser;

  public RandomUserClientProperty getRandomUser() {
    return randomUser;
  }

  public void setRandomUser(RandomUserClientProperty randomUser) {
    this.randomUser = randomUser;
  }
}
