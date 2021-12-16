package com.atguigu.crm.workbench.controller;

import com.atguigu.crm.setting.domain.User;
import com.atguigu.crm.setting.service.UserService;
import com.atguigu.crm.utils.DateTimeUtil;
import com.atguigu.crm.utils.UUIDUtil;
import com.atguigu.crm.workbench.domain.Activity;
import com.atguigu.crm.workbench.domain.Clue;
import com.atguigu.crm.workbench.domain.ClueActivityRelation;
import com.atguigu.crm.workbench.service.ClueService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @create 2021-12-14 11:30
 */
@Controller
@RequestMapping("/workbench")
public class ClueController {

    @Autowired
    ClueService clueService;

    //查询所有user的name
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUserList(){

        List<User> userList = clueService.getUserList();

        return userList;
    }
    /**
     * 通过id查询线索细节
     */
    @RequestMapping(value = "/clue/{id}",method = RequestMethod.GET)
    public String selectClueById(@PathVariable("id") String id, Model model){
        Clue clue = clueService.selectClueById(id);
        model.addAttribute("clue",clue);
        return "workbench/clue/detail";
    }
    /**
     * 分页查询线索
     */
    @RequestMapping(value = "/clue",method = RequestMethod.GET)
    @ResponseBody
    public PageInfo selectClueList(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                          @RequestParam(value = "pageSize",defaultValue = "2") Integer pageSize,
                                          Clue clue){

        PageHelper.startPage(pageNo,pageSize);

        List<Clue> clues = clueService.selectClueList(clue);

        PageInfo pageInfo = new PageInfo(clues,3);

        return pageInfo;
    }

    /**
     * 创建线索
     * @param clue
     * @return
     */
    @RequestMapping(value = "/clue",method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> createClue(Clue clue,HttpSession session){

        //设置ia、创建时间和创建者
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        User user = (User) session.getAttribute("user");
        if (user != null){
            clue.setCreateBy(user.getName());
        }

        boolean flag = clueService.createClue(clue);
        Map<String ,Object> map = new HashMap<>();
        map.put("flag",flag);

        return map;
    }

    /**
     * 通过名称查询clue关联的市场活动信息（支持模糊查询）
     */
    @RequestMapping(value = "/ClueAndActivity/{clueId}/{aname}",method = RequestMethod.GET)
    @ResponseBody
    public List<Activity>  selectActivityByClueId(@PathVariable("clueId") String clueId,
                                                  @PathVariable("aname") String aname){

        System.out.println(clueId);
        System.out.println(aname);
        List<Activity>  activitys = clueService.selectActivityByClueId(clueId,aname);

        return activitys;
    }
    /**
     * 查询clue关联的市场活动信息
     */
    @RequestMapping(value = "/ClueAndActivity/{clueId}",method = RequestMethod.GET)
    @ResponseBody
    public List<Activity>  selectActivityByClueId(@PathVariable("clueId") String clueId){

        List<Activity>  activitys = clueService.selectActivityByClueId(clueId,null);

        return activitys;
    }

    /**
     * 解除线索关联的市场活动信息
     */
    @RequestMapping(value = "/ClueAndActivity/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String ,Object>  deleteRelationByCARId(@PathVariable("id") String CARId){

        boolean flag = clueService.deleteRelationByCARId(CARId);
        Map<String ,Object> map = new HashMap<>();
        map.put("flag",flag);
        return map;
    }

    /**
     * 通过名称查询此线索未关联的市场活动（支持模糊查询），
     */
    @RequestMapping(value = "/ClueAndActivity",method = RequestMethod.GET)
    @ResponseBody
    public PageInfo  selectActivityByName(Integer pageNo,Integer pageSize,String clueId,String aname){

        PageHelper.startPage(pageNo,pageSize);
        List<Activity>  activitys = clueService.selectActivityByName(clueId,aname);
        PageInfo pageInfo = new PageInfo(activitys,3);

        return pageInfo;
    }

    /**
     * 创建 线索与市场活动关联
     * @return
     */
    @RequestMapping(value = "/ClueAndActivity",method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> createClueAndActivity(String clueId, String[] activityId){

        List<ClueActivityRelation> list = new ArrayList<>();
        for (int i = 0; i < activityId.length; i++) {
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setClueId(clueId);
            car.setActivityId(activityId[i]);
            list.add(car);
        }


        boolean flag = clueService.createClueAndActivitys(list);

        Map<String ,Object> map = new HashMap<>();
        map.put("flag",flag);

        return map;
    }
}
