package com.edu.rabbitmq;

import com.edu.rabbitmq.MessageProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @Author: 何有悠然
 * @ClassName: com.edu.rabbitmq.AMQPTest
 * @CreateDate: 2018/8/15 9:47
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class AMQPTest {
    private static Logger LOGGER = null;

    @Resource(name = "messageProducer")
    private MessageProducer messageProducer;

    @BeforeClass
    public static void setLogger() throws MalformedURLException {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        LOGGER = LogManager.getLogger();
    }

    @Test
    public void sendMessage() throws IOException {
        int a = 1000;
        while (a > 0) {
            messageProducer.sendMessage("Hello, I am amq sender num :" + a--);
            try {
                //暂停一下，好让消息消费者去取消息打印出来
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
