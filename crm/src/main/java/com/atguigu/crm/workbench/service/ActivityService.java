package com.atguigu.crm.workbench.service;

import com.atguigu.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @create 2021-12-09 22:23
 */
public interface ActivityService {

    List<Map<String, Object>> selectActivity(Activity activity);

    boolean createActivity(Activity activity);

    /**
     * 删除选中的市场活动，
     * 并删除市场活动备注，以及市场活动与线索、联系人之间的关联关系
     * @param id
     * @return
     */
    boolean deleteActivity(String[] id);

    Activity selectOneActivity(String id);


    boolean updateActivity(Activity activity);

    Map<String, Object> selectActivityDetail(String id);
}
