package com.atguigu.crm.workbench.service;

import com.atguigu.crm.setting.domain.User;
import com.atguigu.crm.workbench.domain.Activity;
import com.atguigu.crm.workbench.domain.Clue;

import java.util.List;

/**
 * @create 2021-12-14 11:36
 */
public interface ClueService {

    List<User> getUserList();

    boolean createClue(Clue clue);

    List<Clue> selectClueList(Clue clue);
}
