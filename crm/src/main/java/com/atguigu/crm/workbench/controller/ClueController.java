package com.atguigu.crm.workbench.controller;

import com.atguigu.crm.setting.domain.User;
import com.atguigu.crm.setting.service.UserService;
import com.atguigu.crm.utils.DateTimeUtil;
import com.atguigu.crm.utils.UUIDUtil;
import com.atguigu.crm.workbench.domain.Activity;
import com.atguigu.crm.workbench.domain.Clue;
import com.atguigu.crm.workbench.service.ClueService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
}
