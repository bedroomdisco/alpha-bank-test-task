package com.example.richbroken.controller;

import com.example.richbroken.service.RichBrokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "/v1")
public class RichBrokenController {
    private final RichBrokenService richBrokenService;
    public static final Logger logger = LoggerFactory.getLogger(RichBrokenController.class);

    @Autowired
    public RichBrokenController(RichBrokenService richBrokenService) {
        this.richBrokenService = richBrokenService;
    }

    @GetMapping(value = "/currency/{currency}")
    public ResponseEntity<Void> getResult(@PathVariable String currency) {
        String redirectUrl = richBrokenService.compareExchangeRate(currency);
        logger.info("Redirect URL: " + redirectUrl);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirectUrl)).build();
    }

}
