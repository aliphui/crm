package com.atguigu.crm.workbench.dao;

import com.atguigu.crm.workbench.domain.ClueRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueRemarkDao {

    /**
     * 根据clueId查询所有线索备注
     * @param clueId
     * @return
     */
    List<ClueRemark> selectClueRemarkByClueId(@Param("clueId") String clueId);

    int deleteByClueId(@Param("clueId") String clueId);
}
