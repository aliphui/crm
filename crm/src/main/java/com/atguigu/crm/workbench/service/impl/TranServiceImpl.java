package com.atguigu.crm.workbench.service.impl;

import com.atguigu.crm.workbench.dao.TranDao;
import com.atguigu.crm.workbench.domain.Tran;
import com.atguigu.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create 2021-12-18 16:07
 */
@Service
public class TranServiceImpl implements TranService {

    @Autowired
    TranDao tranDao;

    @Override
    public List<Tran> selectTranPage(Tran tran) {
        List<Tran> trans = tranDao.selectTranPage(tran);
        return trans;
    }
}
