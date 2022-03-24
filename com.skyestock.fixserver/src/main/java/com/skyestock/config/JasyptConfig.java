package com.skyestock.config;

import org.springframework.beans.factory.annotation.Value;

public class JasyptConfig {

    @Value("${jasypt.encryptor.password}")
    public String encryptor;

}
