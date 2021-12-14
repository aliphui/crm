package com.atguigu.crm.workbench.dao;

import com.atguigu.crm.workbench.domain.ActivityRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @create 2021-12-13 13:06
 */
public interface ActivityRemarkDao {
    int addActivityRemark(ActivityRemark activityRemark);

    List<ActivityRemark> selectActivityRemark(@Param("activityId")String activityId);

    int deleteActivityRemark(@Param("id") String id);

    int updateActivityRemark(ActivityRemark activityRemark);
}
