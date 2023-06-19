package com.bezkoder.springjwt.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/trans")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/test2")
    public String test3(){
        return "aaa";
    }

    @GetMapping("/postOne")
    public String postIt(){
        String aaa="hello world";
        return aaa;
    }
}
