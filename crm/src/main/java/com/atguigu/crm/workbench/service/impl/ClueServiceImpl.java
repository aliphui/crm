package com.atguigu.crm.workbench.service.impl;

import com.atguigu.crm.setting.dao.UserDao;
import com.atguigu.crm.setting.domain.User;
import com.atguigu.crm.workbench.dao.ActivityDao;
import com.atguigu.crm.workbench.dao.ClueActivityRelationDao;
import com.atguigu.crm.workbench.dao.ClueDao;
import com.atguigu.crm.workbench.domain.Activity;
import com.atguigu.crm.workbench.domain.Clue;
import com.atguigu.crm.workbench.domain.ClueActivityRelation;
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

    @Autowired
    ActivityDao activityDao;

    @Autowired
    ClueActivityRelationDao clueActivityRelationDao;

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

    @Override
    public Clue selectClueById(String id) {
        return clueDao.selectClueAndUserByClueId(id);
    }

    @Override
    public List<Activity>  selectActivityByClueId(String clueId,String aname) {

        return activityDao.selectActivityByClueId(clueId,aname);
    }

    @Override
    public boolean deleteRelationByCARId(String carId) {
        int count  = clueDao.deleteRelationByCARId(carId);
        if (count != 1){
            return false;
        }
        return true;
    }

    @Override
    public List<Activity> selectActivityByName(String clueId, String aname) {

        return activityDao.selectActivityByName(clueId,aname);
    }

    @Override
    public boolean createClueAndActivitys(List<ClueActivityRelation> list) {

        int count = clueActivityRelationDao.createClueAndActivitys(list);
        if (count < 1){
            return false;
        }
        return true;
    }
}
