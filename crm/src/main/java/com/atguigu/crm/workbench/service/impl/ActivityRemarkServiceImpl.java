package com.atguigu.crm.workbench.service.impl;

import com.atguigu.crm.workbench.dao.ActivityRemarkDao;
import com.atguigu.crm.workbench.domain.ActivityRemark;
import com.atguigu.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create 2021-12-13 12:59
 */
@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    ActivityRemarkDao activityRemarkDao;
    @Override
    public boolean addActivityRemark(ActivityRemark activityRemark) {
        int  count = activityRemarkDao.addActivityRemark(activityRemark);
        if (count != 1){
            return false;
        }
        return true;
    }

    @Override
    public List<ActivityRemark> selectActivityRemark(String activityId) {

        return  activityRemarkDao.selectActivityRemark(activityId);
    }


    @Override
    public boolean updateActivityRemark(ActivityRemark activityRemark) {
        int  count = activityRemarkDao.updateActivityRemark(activityRemark);
        if (count != 1){
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteActivityRemark(String id) {
        int  count = activityRemarkDao.deleteActivityRemark(id);
        if (count != 1){
            return false;
        }
        return true;
    }


}
