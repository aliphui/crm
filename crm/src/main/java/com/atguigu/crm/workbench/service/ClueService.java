package com.atguigu.crm.workbench.service;

import com.atguigu.crm.setting.domain.User;
import com.atguigu.crm.workbench.domain.Activity;
import com.atguigu.crm.workbench.domain.Clue;
import com.atguigu.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

/**
 * @create 2021-12-14 11:36
 */
public interface ClueService {

    List<User> getUserList();

    boolean createClue(Clue clue);

    List<Clue> selectClueList(Clue clue);

    Clue selectClueById(String id);

    List<Activity>  selectActivityByClueId(String clueId,String aname);

    boolean deleteRelationByCARId(String carId);

    List<Activity> selectActivityByName(String clueId, String aname);

    boolean createClueAndActivitys(List<ClueActivityRelation> list);

}
