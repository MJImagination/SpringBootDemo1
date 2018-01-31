package com.mj.controller;

import com.alibaba.fastjson.JSON;
import com.mj.activityMQ.LogProducer;
import com.mj.entity.User;
import com.mj.repository.UserRepositoty;
import com.mj.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ancun on 2017/2/24.
 */
@Controller
@RequestMapping(value = "/mq")
public class ActivityMqController {
    @Autowired
    private LogProducer logProducer;

    @GetMapping("/activemq/send")
    @ResponseBody
    public String activemq(HttpServletRequest request, String msg) {
        msg = StringUtils.isEmpty(msg) ? "This is Empty Msg." : msg;


        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 1000; i++) {
            logProducer.send(msg + " = " + i);
        }
        stopWatch.stop();
        System.out.println("发送消息耗时: " + stopWatch.getTotalTimeMillis() + "ms");
        return "Activemq has sent OK.";
    }
}
