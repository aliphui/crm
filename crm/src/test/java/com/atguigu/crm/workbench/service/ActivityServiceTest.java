package com.atguigu.crm.workbench.service;

import com.atguigu.crm.workbench.dao.ActivityDao;
import com.atguigu.crm.workbench.domain.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @create 2021-12-12 12:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dao.xml"})
public class ActivityServiceTest {

    @Autowired
    ActivityDao activityDao;
    @Test
    public void deleteActivity() {
        String[] id = {};
        int i = activityDao.deleteActivity(id);
        System.out.println(i);

    }
    @Test
    public void selectActivity() {
        Activity activity = new Activity();
        List<Map<String, Object>> maps = activityDao.selectActivity(activity);
        System.out.println(maps);

    }
}