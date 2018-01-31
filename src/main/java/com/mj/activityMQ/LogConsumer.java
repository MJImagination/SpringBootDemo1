package com.mj.activityMQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  
import org.springframework.jms.annotation.JmsListener;  
import org.springframework.stereotype.Component;  
  
@Component  
public class LogConsumer {  
  
    private static final Logger LOGGER = LoggerFactory.getLogger(LogConsumer.class);  

    /*定义2个消费者消费First_Queue*/
    @JmsListener(destination = QueueName.First_Queue)
    public void receivedQueue(String msg) {  
        LOGGER.info("First_Queue Has received from " + QueueName.First_Queue + ", msg: " + msg);
    }

    @JmsListener(destination = QueueName.First_Queue)
    public void receivedQueue2(String msg) {
        LOGGER.info("First_Queue2 Has received from " + QueueName.First_Queue + ", msg: " + msg);
    }

    /*定义1个消费者消费Second_Queue*/
    @JmsListener(destination = QueueName.Second_Queue)
    public void receivedQueue3(String msg) {
        LOGGER.info("Second_Queue Has received from " + QueueName.Second_Queue + ", msg: " + msg);
    }
}  