package com.atguigu.crm.workbench.service;

import com.atguigu.crm.setting.domain.User;
import com.atguigu.crm.workbench.domain.Activity;
import com.atguigu.crm.workbench.domain.Clue;
import com.atguigu.crm.workbench.domain.ClueActivityRelation;
import com.atguigu.crm.workbench.domain.Tran;

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

    /**
     * 删除指定id的clue，并删除线索的备注、线索与市场活动的关联关系
     * @param id
     * @return
     */
    boolean deleteClueById(String id);

    /**
     * 转换指定id的线索
     * 1.线索——转换为客户和联系人，并删除线索
     * 2.此线索备注——转换为客户和联系人备注，并删除
     * 3.此线索与市场活动的关联关系——转换为，联系人-市场活动关联关系，并删除
     *
     * @param clueId 需要转换的线索的id
     * @param createBy 客户和联系人的创建者
     * @param tran 如果不为null，创建交易和交易历史
     * @return
     */
    boolean converClue(String clueId, String createBy,Tran tran);
}
