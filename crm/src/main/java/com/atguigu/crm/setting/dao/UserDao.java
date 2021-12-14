package com.atguigu.crm.setting.dao;

import com.atguigu.crm.setting.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @create 2021-12-08 14:07
 */
public interface UserDao {
    User selectUserForActAndPwd(@Param("loginAct") String loginAct, @Param("loginPwd") String loginPwd);

    List<User> selectUserList();
}
