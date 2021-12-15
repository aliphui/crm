package com.atguigu.crm.workbench.dao;


import com.atguigu.crm.workbench.domain.Activity;
import com.atguigu.crm.workbench.domain.Clue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueDao {


    int createClue(Clue clue);

    List<Clue> selectClueList(Clue clue);

    Clue selectClueAndUserByClueId(@Param("id") String id);

    int deleteRelationByCARId(String CARId);
}
