package com.morebooks.morebooks.controllers;

import com.morebooks.morebooks.clients.domain.VolumeQuery;
import com.morebooks.morebooks.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test/book")
    public VolumeQuery queryBooks(@RequestParam(required = false, value = "q") String query) {
        List<Book> books = null;

//        "https://www.googleapis.com/books/v1/volumes?q=harry+potter"
        ResponseEntity<VolumeQuery> res = restTemplate.getForEntity("https://www.googleapis.com/books/v1/volumes?q=" + query, VolumeQuery.class);

        System.out.println(res.getStatusCode());
        VolumeQuery out = res.getBody();
        if(out != null) {
            System.out.println(res.getBody().toString());
        }

        return out;
    }

}
