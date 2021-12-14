package com.atguigu.crm.workbench.dao;


import com.atguigu.crm.workbench.domain.Activity;
import com.atguigu.crm.workbench.domain.Clue;

import java.util.List;

public interface ClueDao {


    int createClue(Clue clue);

    List<Clue> selectClueList(Clue clue);
}
