package com.atguigu.crm.setting.controller;

import com.atguigu.crm.exception.MyException;
import com.atguigu.crm.setting.domain.User;
import com.atguigu.crm.setting.service.UserService;
import com.atguigu.crm.setting.service.impl.UserServiceImpl;
import com.atguigu.crm.utils.IPUtil;
import com.atguigu.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @create 2021-12-08 14:14
 */
@Controller
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(@RequestParam String loginAct, @RequestParam String loginPwd,
                                    HttpServletRequest request, Model model){

        String ip = null;
        try {
            ip = IPUtil.getIp(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<String, Object>();

        String loginMsg = null;
        try {
            User user = userService.login(loginAct, loginPwd, ip);
            request.getSession().setAttribute("user",user);
            loginMsg = "success";
        } catch (MyException e) {
            loginMsg = e.getMessage();
        }

        map.put("msg",loginMsg);

        return map;
    }
}
