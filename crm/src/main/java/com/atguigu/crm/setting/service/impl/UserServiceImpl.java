package com.atguigu.crm.setting.service.impl;

import com.atguigu.crm.exception.MyException;
import com.atguigu.crm.setting.dao.UserDao;
import com.atguigu.crm.setting.domain.User;
import com.atguigu.crm.setting.service.UserService;
import com.atguigu.crm.utils.DateTimeUtil;
import com.atguigu.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import java.util.List;

/**
 * @create 2021-12-08 14:10
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Override
    public User login(String loginAct, String loginPwd,String ip) throws MyException {

        String md5Pwd = MD5Util.getMD5(loginPwd);

        User user = userDao.selectUserForActAndPwd(loginAct,md5Pwd);

        if (user == null){
            throw new MyException("用户名或密码错误！！！");
        }else if(DateTimeUtil.getSysTime().compareTo(user.getExpireTime()) < 0){
            throw new MyException("该账户已失效！！！");
        }else if(user.getLockState() == "0"){
            throw new MyException("该账户已锁定！！！");
        }else if(!user.getAllowIps().contains(ip)){
            throw new MyException("登录ip地址错误！！！");
        }

        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = userDao.selectUserList();
        return userList;
    }
}
