package com.example.nasafeignclient.controllers;

import com.example.nasafeignclient.client.PictureClient;
import com.example.nasafeignclient.entity.Photo;
import com.example.nasafeignclient.entity.Photos;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Import(FeignClientsConfiguration.class)
public class NasaPictureController {
    RestTemplate restTemplate = new RestTemplate();
    String url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos";

    @Autowired
    PictureClient pictureClient;

    @SneakyThrows
    public byte[] getLargestPicture(String sol, String camera) {
        var photosEntity = pictureClient.getPicturesList(sol, camera, "DEMO_KEY");
        Photos photos = photosEntity.getBody();
        List<Photo> photoList = photos.getPhotos();

        System.out.println("Photos qty: " + photoList.size());

        Map<String,Long> photosMap = new HashMap<>();

        for (Photo photo : photoList) {
            long size = getPictureSize(photo.getImg_src());
            photosMap.put(photo.getImg_src(), size);
        }

        var biggestPicture = photosMap.entrySet().stream().max(
                (pict1, pict2) -> {
                    if (pict1.getValue().equals(pict2.getValue())) return 0;
                    return (pict1.getValue() > pict2.getValue()) ? 1 : -1;
                }
        ).orElseThrow();
        return getPicture(biggestPicture.getKey());
    }

    private long getPictureSize(String url) throws URISyntaxException {
        long size;
        var responseEntity = restTemplate.exchange(new URI(url), HttpMethod.HEAD, null, Void.class);
        var statusCode = responseEntity.getStatusCode();
        if(statusCode.equals(HttpStatus.MOVED_PERMANENTLY)) {
            size = getPictureSize(responseEntity.getHeaders().getLocation().toString());
        } else {
            size = responseEntity.getHeaders().getContentLength();
        }
        return size;
    }

    private byte[] getPicture(String url) throws URISyntaxException {
        byte[] body;
        var responseEntity= restTemplate.getForEntity(new URI(url), byte[].class);
        var statusCode = responseEntity.getStatusCode();
        if(statusCode.equals(HttpStatus.MOVED_PERMANENTLY)) {
            body = getPicture(responseEntity.getHeaders().getLocation().toString());
        } else {
            body = responseEntity.getBody();
        }
        return body;
    }
}
