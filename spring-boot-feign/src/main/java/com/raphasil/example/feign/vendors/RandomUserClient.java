package com.raphasil.example.feign.vendors;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "https://randomuser.com/api/", name = "randomuser")
public interface RandomUserClient {

  @GetMapping
  ResponseEntity<String> get();

}
