package com.morebooks.morebooks.clients.domain;

import lombok.Data;

import java.util.List;

@Data
public class VolumeInfo {

    private String title;
    private String subtitle;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private Integer pageCount;
    private ImageLinks imageLinks;
}
