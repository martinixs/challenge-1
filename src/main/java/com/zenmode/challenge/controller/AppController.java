package com.zenmode.challenge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping(path = "/app")
    public String index() {
        return "index";
    }
}
