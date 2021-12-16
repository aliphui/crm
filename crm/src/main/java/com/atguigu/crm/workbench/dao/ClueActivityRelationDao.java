package com.atguigu.crm.workbench.dao;


import com.atguigu.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int createClueAndActivitys(List<ClueActivityRelation> list);

}
