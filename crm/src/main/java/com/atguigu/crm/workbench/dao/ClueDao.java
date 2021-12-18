package com.atguigu.crm.workbench.dao;


import com.atguigu.crm.workbench.domain.Activity;
import com.atguigu.crm.workbench.domain.Clue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueDao {


    int createClue(Clue clue);

    List<Clue> selectClueList(Clue clue);

    Clue selectClueAndUserByClueId(@Param("id") String id);

    /**
     * 根据clueId查询clue
     * @param clueId
     * @return
     */
    Clue selectClueById(@Param("clueId") String clueId);

    /**
     * 通过id删除clue
     * @param id
     * @return
     */
    int deleteById(@Param("id") String id);
}
