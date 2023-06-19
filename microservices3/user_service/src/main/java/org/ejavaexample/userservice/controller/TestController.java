package org.ejavaexample.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/user")
public class TestController {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/test2")
    public String test(@RequestHeader("Authorization") String tokenBearer){
        String token=tokenBearer.substring(7, tokenBearer.length());
        String userName=restTemplate.getForObject("http://AUTH-SERVICE:8001/api/auth/getUserId/"+token,String.class);
        String aaa=restTemplate.getForObject("http://PRODUCT-SERVICE:8083/api/product/test",String.class);
        return userName + ":" + aaa;
    }

    @GetMapping("/get-test2")
    public String getTest(){
        String aaa=restTemplate.getForObject("http://TRANS-SERVICE:8079/trans/postOne",String.class);
        return aaa;
    }
}
