package com.mj.Aop;

import com.mj.entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * publicity保存切面
 * @author MJ
 * @Description:
 * @Date: create 2018/1/15
 */
@Aspect
@Component
public class SavePublicityAspect {
    public static  final Logger logger = LoggerFactory.getLogger(SavePublicityAspect.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @Pointcut("@annotation(com.mj.Aop.Annotation.SavePublicityData)")
    public void SavePublicityPointCut() {
    }

    /**
     * 保存系统日志到数据库
     *
     * @param joinPoint 切点
     */
    @AfterReturning("SavePublicityPointCut()")
    public void sendWebSocketMessage(JoinPoint joinPoint) {
        // 获取目标方法体参数
        Object[] args = joinPoint.getArgs();
        User user = (User) args[0];
        if(user!=null){
            try {
                callback(user);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    @SendTo("/topic/callback")
    public Object callback(User user) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/callback", user);
        logger.info("广播消息到客户端，内容：{}",user);
        return "callback";
    }
}
