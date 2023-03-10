package com.example.demo.controller;

import com.example.demo.Chatter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于接收前端发送的消息
 */
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class ChatController {
    Chatter user;
    public void send(@RequestBody String body){
        user.sendMsg(body);
    }
}
