package com.mj.controller;

import com.alibaba.fastjson.JSON;
import com.mj.entity.User;
import com.mj.repository.UserRepositoty;
import com.mj.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ancun on 2017/2/24.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private UserRepositoty userRepositoty;

    @RequestMapping(value = "/index")
    public String index(Model model ){
        User user = userRepositoty.findAll().get(0);
        model.addAttribute("user",user);
        return "user/index";
    }

    @RequestMapping(value = "/show",method = RequestMethod.GET)
    @ResponseBody
    public String show(@RequestParam(value = "name")String name){
        User user = userService.findUserByName(name);
        if(null != user){

            return JSON.toJSONString(user)+"hhh";

        }else{
         return "null";
        }
    }
}
