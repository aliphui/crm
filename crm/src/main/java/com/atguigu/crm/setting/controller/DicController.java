package com.atguigu.crm.setting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @create 2021-12-15 10:14
 */
@Controller
@RequestMapping("/setting")
public class DicController {


    //获取指定的数据字典类型的数据
    @RequestMapping("/dic")
    @ResponseBody
    public Map<String, List> selectDic(HttpServletRequest request){

        ServletContext servletContext = request.getServletContext();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, List> map = new HashMap<>();

        for (String[] s : parameterMap.values()){
            List list = (List)servletContext.getAttribute(s[0]);
            map.put(s[0],list);
        }

        return map;
    }


}
