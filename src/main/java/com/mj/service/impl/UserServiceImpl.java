package com.mj.service.impl;

import com.mj.entity.User;
import com.mj.repository.UserRepositoty;
import com.mj.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ancun on 2017/12/24.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepositoty userRepositoty;

    @Override
    public User findUserByName(String name){
        User user = null;
        try{
            user = userRepositoty.findByUserName(name);
        }catch (Exception e){}
        return user;
    }
}
