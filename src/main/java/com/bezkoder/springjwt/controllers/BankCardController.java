package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.service.IBankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangwq
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bank-card")
public class BankCardController {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    IBankCardService bankCardService;


}
