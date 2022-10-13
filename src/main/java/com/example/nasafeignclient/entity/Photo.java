package com.example.nasafeignclient.entity;

import lombok.Data;

@Data
public class Photo {
    private long id;
    private int sol;
    private String img_src;
}
