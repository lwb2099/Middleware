package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws JMSException {
        SpringApplication.run(DemoApplication.class, args);
    }

}
