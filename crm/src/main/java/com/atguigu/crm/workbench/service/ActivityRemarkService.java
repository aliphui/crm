package com.atguigu.crm.workbench.service;

import com.atguigu.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @create 2021-12-13 12:59
 */
public interface ActivityRemarkService {
    boolean addActivityRemark(ActivityRemark activityRemark);

    List<ActivityRemark> selectActivityRemark(String activityId);

    boolean deleteActivityRemark(String id);

    boolean updateActivityRemark(ActivityRemark activityRemark);

}
