package com.atguigu.crm.workbench.dao;

import com.atguigu.crm.workbench.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @create 2021-12-09 22:13
 */
public interface ActivityDao {
    /**
     * 查询所有市场活动
     * @return
     */
    List<Map<String,Object>> selectActivity(Activity activity);

    int createActivity(Activity activity);

    int deleteActivity(String[] id);

    Activity selectOneActivity(@Param(value = "id") String id);

    int updateActivity(Activity activity);

    Map<String, Object> selectActivityDetailById(@Param(value = "id")String id);

    List<Activity> selectActivityByClueId(@Param("clueId") String clueId,@Param("aname")String aname);

    List<Activity> selectActivityByName(@Param("clueId") String clueId, @Param("aname") String aname);
}
