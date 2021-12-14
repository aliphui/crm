package com.atguigu.crm.setting.dao;

import com.atguigu.crm.setting.domain.DicType;

import java.util.List;

/**
 * @create 2021-12-14 21:58
 */
public interface DicTypeDao {
    List<DicType> selectDicTypeList();
}
