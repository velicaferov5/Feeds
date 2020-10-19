package com.feeds.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class CheckController {

    @RequestMapping("/")
    public String home() {
        return "Feeds API is working!";
    }
}
