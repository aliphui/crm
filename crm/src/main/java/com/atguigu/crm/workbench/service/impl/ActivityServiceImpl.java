package com.atguigu.crm.workbench.service.impl;

import com.atguigu.crm.workbench.dao.ActivityDao;
import com.atguigu.crm.workbench.domain.Activity;
import com.atguigu.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

/**
 * @create 2021-12-09 22:24
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityDao activityDao;
    @Override
    public List<Map<String, Object>> selectActivity(Activity activity) {

        List<Map<String, Object>> activityMap = activityDao.selectActivity(activity);

        return activityMap;
    }

    @Override
    public Activity selectOneActivity(String id) {

        return activityDao.selectOneActivity(id);
    }

    @Override
    public boolean createActivity(Activity activity) {
        int count = activityDao.createActivity(activity);
        if (count != 1){
            return false;
        }
        return true;
    }

    @Override
    public boolean updateActivity(Activity activity) {
        int count = activityDao.updateActivity(activity);
        if (count != 1){
            return false;
        }
        return true;
    }

    @Override
    public Map<String, Object> selectActivityDetail(String id) {
        Map<String, Object> map = activityDao.selectActivityDetailById(id);
        return map;
    }

    @Override
    public boolean deleteActivity(String[] id) {
        int count = activityDao.deleteActivity(id);
        if (count < 1){
            return false;
        }
        return true;
    }
}
