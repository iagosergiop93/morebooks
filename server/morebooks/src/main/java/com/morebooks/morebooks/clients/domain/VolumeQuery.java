package com.morebooks.morebooks.clients.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VolumeQuery {

    private Integer totalItems;

    @JsonProperty(value = "items")
    private List<Volume> volumes;
}
