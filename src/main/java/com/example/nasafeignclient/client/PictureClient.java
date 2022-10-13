package com.example.nasafeignclient.client;

import com.example.nasafeignclient.entity.Photos;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "picture-service", url = "https://api.nasa.gov/")
public interface PictureClient {
    @GetMapping("mars-photos/api/v1/rovers/curiosity/photos")
    ResponseEntity<Photos> getPicturesList(@RequestParam("sol") String sol,
                                           @RequestParam(value = "camera", required = false) String camera,
                                           @RequestParam(value = "api_key") String apiKey);
}
