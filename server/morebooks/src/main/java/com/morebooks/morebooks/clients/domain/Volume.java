package com.morebooks.morebooks.clients.domain;

import lombok.Data;

@Data
public class Volume {

    private String id;
    private String etag;
    private String selfLink;
    private VolumeInfo volumeInfo;

}
