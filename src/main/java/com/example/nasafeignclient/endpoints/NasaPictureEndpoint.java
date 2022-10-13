package com.example.nasafeignclient.endpoints;

import com.example.nasafeignclient.client.PictureClient;
import com.example.nasafeignclient.controllers.NasaPictureController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NasaPictureEndpoint {

    @Autowired
    NasaPictureController nasaController;

    @GetMapping(path = "/mars/pictures/largest")
    public ResponseEntity<byte[]> getLargestPicture(@RequestParam(name = "sol") String sol, @RequestParam(name = "camera", required = false) String camera) {
        var body = nasaController.getLargestPicture(sol, camera);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        ResponseEntity <byte[]> response = new ResponseEntity<>(body, httpHeaders, HttpStatus.OK);
        return response;
    }
}
