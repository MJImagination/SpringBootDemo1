package com.mj.activityMQ;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;

import javax.jms.Destination;
import javax.jms.Queue;

/**
 * 用于定义消息队列
 */
@Configuration  
public class QueueConfig {  
  
    @Bean(name = "First_Queue")
    public Destination First_Queue() {
        return new ActiveMQQueue(QueueName.First_Queue);
    }

    @Bean(name = "Second_Queue")
    public Destination Second_Queue() {
        return new ActiveMQQueue(QueueName.Second_Queue);
    }
}  