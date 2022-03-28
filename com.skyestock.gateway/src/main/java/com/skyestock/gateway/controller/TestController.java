package com.skyestock.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class TestController {

    @Value("${flag}")
    private int flag;

    @GetMapping("/getFlag")
    public String getFlag(){
        return String.valueOf(flag);
    }



}
