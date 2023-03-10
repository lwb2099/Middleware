package com.example.demo.config;

import com.example.demo.Chatter;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageListener;

public class AppConfig {
    // ActiveMQ 支持不同的协议，你可以在它的配置文件中 conf/activemq.xml 找到不同协议的连接方式
    public static String BROKER_URL = "tcp://0.0.0.0:61616";
    public static String USER = "admin";
    public static String PASSWORD = "admin";
    public static String PRICE_TOPIC = "systemA.systemB.Price.Topic";
    public static String DURABLE_SUBSCRIBER = "systemA.systemB.Price.Topic.myDurable";
    public static String CLIENT_ID = "systemB";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setTargetConnectionFactory(activeMQConnectionFactory());
        connectionFactory.setSessionCacheSize(20);

        return connectionFactory;
    }

    private ConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(BROKER_URL);
        factory.setUserName(USER);
        factory.setPassword(PASSWORD);
        factory.setClientID(CLIENT_ID);

        return factory;
    }

    @Bean
    public ActiveMQTopic priceTopic() {
        return new ActiveMQTopic(PRICE_TOPIC);
    }

    @Bean
    public JmsTemplate priceJmsTemplate(ConnectionFactory factory, Topic priceTopic) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(factory);
        jmsTemplate.setDefaultDestination((Destination) priceTopic);

        return jmsTemplate;
    }

    @Bean
    public Chatter msgListener() {
        return new Chatter();
    }

    @Bean
    public DefaultMessageListenerContainer messageListenerContainer(ConnectionFactory factory, Topic priceTopic, MessageListener messageListener) {
        DefaultMessageListenerContainer mlc = new DefaultMessageListenerContainer();
        mlc.setConnectionFactory(factory);
        mlc.setDestination((Destination) priceTopic);
        mlc.setMessageListener(messageListener);
        mlc.setSubscriptionDurable(true);
        mlc.setDurableSubscriptionName(DURABLE_SUBSCRIBER);
        mlc.setClientId(CLIENT_ID);

        return mlc;
    }
}
