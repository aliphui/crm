package com.atguigu.crm.setting.dao;

import com.atguigu.crm.setting.domain.User;
import com.atguigu.crm.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @create 2021-12-09 11:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dao.xml"})
public class UserDaoTest {

    @Autowired
    UserDao userDao;
    @Test
    public void selectUserForActAndPwd() {
        String md5 = MD5Util.getMD5("123");
        System.out.println(md5);

        User user = userDao.selectUserForActAndPwd("ls", md5);
        System.out.println(user);
    }
}