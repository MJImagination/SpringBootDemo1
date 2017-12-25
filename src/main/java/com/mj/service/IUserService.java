package com.mj.service;


import com.mj.entity.User;

/**
 * Created by ancun on 2017/12/24.
 */

public interface IUserService {

    public User findUserByName(String name);

}
