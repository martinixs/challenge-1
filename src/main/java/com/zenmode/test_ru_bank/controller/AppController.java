package com.zenmode.test_ru_bank.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class AppController {

    @RequestMapping(path = "/app", method = RequestMethod.GET)
    public String welcome() {
        return "index";
    }
}
