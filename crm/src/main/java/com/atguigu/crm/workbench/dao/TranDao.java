package com.atguigu.crm.workbench.dao;

import com.atguigu.crm.workbench.domain.Tran;

import java.util.List;

public interface TranDao {

    int createTran(Tran tran);

    List<Tran> selectTranPage(Tran tran);
}
