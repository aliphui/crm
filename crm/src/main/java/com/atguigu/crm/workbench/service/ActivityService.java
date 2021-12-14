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

    boolean deleteActivity(String[] id);

    Activity selectOneActivity(String id);

    boolean updateActivity(Activity activity);

    Map<String, Object> selectActivityDetail(String id);
}
