package com.atguigu.crm.workbench.controller;

import com.atguigu.crm.setting.domain.User;
import com.atguigu.crm.utils.DateTimeUtil;
import com.atguigu.crm.utils.UUIDUtil;
import com.atguigu.crm.workbench.domain.ActivityRemark;
import com.atguigu.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @create 2021-12-13 12:51
 */
@Controller
public class ActivityRemarkController {

    @Autowired
    ActivityRemarkService activityRemarkService;

    @RequestMapping(value = "/activityRemark",method = RequestMethod.GET)
    @ResponseBody
    public List<ActivityRemark> addActivityRemark(@RequestParam String activityId) {

        List<ActivityRemark> activityRemarks = activityRemarkService.selectActivityRemark(activityId);
        return activityRemarks;

    }
    @RequestMapping(value = "/activityRemark",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addActivityRemark(ActivityRemark activityRemark, HttpSession session) {

        //设置主键
        activityRemark.setId(UUIDUtil.getUUID());
        //设置备注创建时间
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        User user = (User) session.getAttribute("user");
        if (user != null){
            //设置备注者
            activityRemark.setCreateBy(user.getName());
        }
        //设置状态，0表示未修改，1表示已修改
        activityRemark.setEditFlag("0");

        boolean flag = activityRemarkService.addActivityRemark(activityRemark);
        Map<String,Object> map = new HashMap<>();
        map.put("flag",flag);

        return map;
    }


    //修改指定ActivityRemark
    @RequestMapping(value = "/activityRemark",method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Boolean> updateActivityRemark(ActivityRemark activityRemark,HttpSession session){

        //设置备注修改时间
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        User user = (User) session.getAttribute("user");
        if (user != null){
            //设置修改者
            activityRemark.setEditBy(user.getName());
        }
        //设置状态，0表示未修改，1表示已修改
        activityRemark.setEditFlag("1");


        boolean flag = activityRemarkService.updateActivityRemark(activityRemark);
        Map<String,Boolean> map = new HashMap<>();
        map.put("flag",flag);

        return map;
    }

    //删除指定activity
    @RequestMapping(value = "/activityRemark",method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Boolean> deleteActivityRemark(@RequestParam String id){

        boolean flag = activityRemarkService.deleteActivityRemark(id);
        Map<String,Boolean> map = new HashMap<>();
        map.put("flag",flag);

        return map;
    }
}
