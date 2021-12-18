package com.atguigu.crm.workbench.service;

import com.atguigu.crm.workbench.domain.Tran;

import java.util.List;

/**
 * @create 2021-12-18 16:06
 */
public interface TranService {
    List<Tran> selectTranPage(Tran tran);

}
