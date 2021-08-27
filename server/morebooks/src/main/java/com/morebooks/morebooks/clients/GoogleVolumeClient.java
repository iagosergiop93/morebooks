package com.morebooks.morebooks.clients;

import com.morebooks.morebooks.clients.domain.Volume;
import com.morebooks.morebooks.clients.domain.VolumeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleVolumeClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${apis.google.volume}")
    private String apiUrl;

    public ResponseEntity<VolumeQuery> queryVolume(String encodedQueryString) {

        String uri = apiUrl + "?" + encodedQueryString;
        ResponseEntity<VolumeQuery> res = restTemplate.getForEntity(uri, VolumeQuery.class);

        return res;
    }

    public ResponseEntity<Volume> getVolumeById(String bookId) {

        String uri = apiUrl + "/" + bookId;
        ResponseEntity<Volume> res = restTemplate.getForEntity(uri, Volume.class);

        return res;
    }

}
