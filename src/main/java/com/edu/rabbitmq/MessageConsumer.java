package com.edu.rabbitmq;


import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @Author: 何有悠然
 * @ClassName: MessageConsumer
 * @CreateDate: 2018/8/15 9:25
 **/
public class MessageConsumer implements MessageListener {
    private Logger logger = LogManager.getLogger(MessageConsumer.class);
    @Override
    public void onMessage(Message message) {

        logger.info("consumer receive message------->:{}", message);

    }

}