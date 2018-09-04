package com.edu.rabbitmq;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service(value = "messageProducer")
public class MessageProducer {
    private Logger logger = LogManager.getLogger(MessageConsumer.class);

    @Resource(name = "amqpTemplate")
    private AmqpTemplate amqpTemplate;


    public void sendMessage(Object message) throws IOException {
        logger.info("to send message:{}", message);
        //发送routing key  与 queue key  绑定
        amqpTemplate.convertAndSend("queuekey1", message);
        amqpTemplate.convertAndSend("queuekey2", message);
    }
}