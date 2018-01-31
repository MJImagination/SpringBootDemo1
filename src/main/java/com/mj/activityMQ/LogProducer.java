package com.mj.activityMQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.boot.CommandLineRunner;  
import org.springframework.jms.core.JmsMessagingTemplate;  
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Queue;
  
@Component
public class LogProducer implements CommandLineRunner {  
  /*CommandLineRunner  ：http://blog.csdn.net/catoop/article/details/50501710*/
    private static final Logger LOGGER = LoggerFactory.getLogger(LogProducer.class);  
  
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Resource(name = "First_Queue")
    private Queue First_Queue;

    @Resource(name = "Second_Queue")
    private Queue Second_Queue;
  
    @Override  
    public void run(String... strings) throws Exception {  
        send("This is a log message.");  
        LOGGER.info("Log Message was sent to the Queue named sample.log");  
    }  

    /*发送消息到定义好的队列中*/
    public void send(String msg) {  
        this.jmsMessagingTemplate.convertAndSend(this.First_Queue, msg);
    }

    public void send2(String msg) {
        this.jmsMessagingTemplate.convertAndSend(this.Second_Queue, msg);
    }
    /*动态定义消息队列名*/
    public void sendMessage(Destination destination, final String message){
        jmsMessagingTemplate.convertAndSend(destination, message);
    }
}  