package com.atguigu.crm.setting.service;

import com.atguigu.crm.exception.MyException;
import com.atguigu.crm.setting.domain.User;

import java.util.List;

/**
 * @create 2021-12-08 14:10
 */
public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws MyException;

    List<User> getUserList();
}
