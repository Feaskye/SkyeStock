package com.skyestock.controller;


import com.skyestock.config.JasyptConfig;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;

@RestController
@Controller
public class TestController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    JasyptConfig jasyptConfig;

    public String test(String pass){
        BasicTextEncryptor textEncryptor=new BasicTextEncryptor();
        //加密所需的salt 盐
        textEncryptor.setPassword(jasyptConfig.encryptor);
        return "加密前："+textEncryptor.encrypt(pass);
    }


}
