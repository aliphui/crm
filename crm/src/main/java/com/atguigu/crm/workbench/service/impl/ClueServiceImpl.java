package com.atguigu.crm.workbench.service.impl;

import com.atguigu.crm.setting.dao.UserDao;
import com.atguigu.crm.setting.domain.User;
import com.atguigu.crm.workbench.dao.ClueDao;
import com.atguigu.crm.workbench.domain.Activity;
import com.atguigu.crm.workbench.domain.Clue;
import com.atguigu.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create 2021-12-14 11:36
 */
@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    ClueDao clueDao;

    @Autowired
    UserDao userDao;

    @Override
    public List<User> getUserList() {
        List<User> userList = userDao.selectUserList();
        return userList;
    }

    @Override
    public boolean createClue(Clue clue) {
        int count = clueDao.createClue(clue);

        if (count != 1){
            return false;
        }
        return true;
    }

    @Override
    public List<Clue> selectClueList(Clue clue) {
        List<Clue> clues = clueDao.selectClueList(clue);
        return clues;
    }
}
