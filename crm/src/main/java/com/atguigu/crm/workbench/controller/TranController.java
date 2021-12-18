package com.atguigu.crm.workbench.controller;

import com.atguigu.crm.workbench.domain.Tran;
import com.atguigu.crm.workbench.service.TranService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @create 2021-12-18 13:33
 */
@Controller
@RequestMapping("/workbench")
public class TranController {


    @Autowired
    TranService tranService;
    /**
     * 分页查询交易，
     * @param pageNo 当前页码
     * @param pageSize 每页记录数
     * @param tran 按tran里面封装的信息，进行条件查询
     * @return
     */
    @RequestMapping("/tran")
    @ResponseBody
    public PageInfo selectTranPage(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                 @RequestParam(value = "pageSize",defaultValue = "2") Integer pageSize,
                                 Tran tran){

        PageHelper.startPage(pageNo,pageSize);

        List<Tran> trans = tranService.selectTranPage(tran);

        PageInfo pageInfo = new PageInfo(trans,3);

        return pageInfo;
    }
}
