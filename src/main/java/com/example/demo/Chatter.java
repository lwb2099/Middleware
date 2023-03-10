package com.example.demo;

import com.example.demo.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class Chatter implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(Chatter.class);
    private int id;
    private String name;

    /**
     * 发送消息
     * @param body
     */
    public void sendMsg(String body){
        // 实例化 Spring IoC 容器
        @SuppressWarnings("resource")
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        JmsTemplate JmsTemplate = context.getBean(JmsTemplate.class);

        // 发送消息
        JmsTemplate.convertAndSend("hello topic world");

        System.exit(0);
    }
    /**
     * 接受消息后的函数
     * TODO:待实现文件传输
     * @param msg
     */
    @Override
    public void onMessage(Message msg) {
        if (msg instanceof TextMessage) {
            String body;
            try {
                body = ((TextMessage) msg).getText();
                System.out.println(body);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Unexpected message type: " + msg.getClass());
        }
    }
    /**
     * TODO:
     */
    public void sendFile(){

    }
}
