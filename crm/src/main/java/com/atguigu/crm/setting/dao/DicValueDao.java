package com.atguigu.crm.setting.dao;

import com.atguigu.crm.setting.domain.DicValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @create 2021-12-14 21:58
 */
public interface DicValueDao {

    List<DicValue> selectDicValueForType(@Param("typeCode") String typeCode);

}
