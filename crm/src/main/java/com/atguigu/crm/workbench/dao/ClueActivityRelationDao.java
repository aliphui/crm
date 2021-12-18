package com.atguigu.crm.workbench.dao;


import com.atguigu.crm.workbench.domain.ClueActivityRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueActivityRelationDao {


    int createClueAndActivitys(List<ClueActivityRelation> list);

    List<ClueActivityRelation> selectClueActivityRelationByClueId(String clueId);


    /**
     * 通过id删除记录
     * @param id
     * @return
     */
    int deleteById(@Param("id") String id);

    /**
     * 通过clueId删除记录
     * @param clueId
     * @return
     */
    int deleteByClueId(@Param("clueId") String clueId);
}
