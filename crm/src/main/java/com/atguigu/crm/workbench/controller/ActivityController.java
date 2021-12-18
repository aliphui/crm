package com.atguigu.crm.workbench.controller;

import com.atguigu.crm.setting.domain.User;
import com.atguigu.crm.setting.service.UserService;
import com.atguigu.crm.utils.DateTimeUtil;
import com.atguigu.crm.utils.UUIDUtil;
import com.atguigu.crm.workbench.domain.Activity;
import com.atguigu.crm.workbench.service.ActivityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @create 2021-12-09 22:27
 */
@Controller
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    UserService userService;

    //查询所有user
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUserList(){

        List<User> userList = userService.getUserList();

        return userList;
    }

    //查询指定id的activity
    @RequestMapping(value = "/selectOneActivity",method = RequestMethod.GET)
    @ResponseBody
    public Activity selectActivity(@RequestParam String id){


        Activity activity = activityService.selectOneActivity(id);

        return activity;
    }
    //分页查询activity
    @RequestMapping(value = "/activity",method = RequestMethod.GET)
    @ResponseBody
    public PageInfo selectActivity(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                   @RequestParam(value = "pageSize",defaultValue = "2") Integer pageSize,
                                   Activity activity){


        PageHelper.startPage(pageNo,pageSize);
        List<Map<String, Object>> activitieMap = activityService.selectActivity(activity);
        PageInfo pageInfo = new PageInfo(activitieMap, 5);

        return pageInfo;
    }

    //添加activity
    @RequestMapping(value = "/activity",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Boolean> createActivity(Activity activity, HttpSession session){

        //生成id，设置id属性
        activity.setId(UUIDUtil.getUUID());
        //生成创建时间，设置创建时间
        activity.setCreateTime(DateTimeUtil.getSysTime());
        //获取session中的user，设置创建者
        User user = (User) session.getAttribute("user");
        if (user != null){
            activity.setCreateBy(user.getName());
        }

        //调用service层保存数据
        boolean flag = activityService.createActivity(activity);
        Map<String,Boolean> map = new HashMap<>();
        map.put("flag",flag);

        return map;
    }

    //修改指定activity
    @RequestMapping(value = "/activity",method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Boolean> updateActivity(Activity activity, HttpSession session){

        //设置修改时间
        activity.setEditTime(DateTimeUtil.getSysTime());
        //设置修改者
        User user = (User) session.getAttribute("user");
        if (user != null){
            activity.setEditBy(user.getName());
        }
        boolean flag = activityService.updateActivity(activity);
        Map<String,Boolean> map = new HashMap<>();
        map.put("flag",flag);
        return map;
    }

    //删除指定activity
    @RequestMapping(value = "/activity",method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Boolean> deleteActivity(@RequestParam String[] id){

        boolean flag = activityService.deleteActivity(id);
        Map<String,Boolean> map = new HashMap<>();
        map.put("flag",flag);

        return map;
    }

    //查询指定activity细节
    @RequestMapping( "/selectActivityDetail")
    public String selectActivityDetail(@RequestParam String id, Model model){

        Map<String,Object> map = activityService.selectActivityDetail(id);
        List<User> userList = userService.getUserList();
        model.addAttribute("map",map);
        model.addAttribute("userList",userList);

        return "workbench/activity/detail";
    }

}
