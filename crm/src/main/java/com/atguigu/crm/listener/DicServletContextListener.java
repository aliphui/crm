package com.atguigu.crm.listener;

import com.atguigu.crm.setting.dao.DicTypeDao;
import com.atguigu.crm.setting.dao.DicValueDao;
import com.atguigu.crm.setting.domain.DicType;
import com.atguigu.crm.setting.domain.DicValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工程启动时，将数据库中数据字典的数据加载到servletContext上下文对象域中
 *
 * @create 2021-12-14 18:44
 */
@Component
public class DicServletContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent event) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-dao.xml");
        DicTypeDao dicTypeDao = context.getBean(DicTypeDao.class);
        DicValueDao dicValueDao = context.getBean(DicValueDao.class);

        //获取上下文域对象
        ServletContext servletContext = event.getServletContext();

        Map<String ,List> map = new HashMap<>();
        //获取全部数据字典类型
        List<DicType> dicTypeList = dicTypeDao.selectDicTypeList();

        //遍历数据字典类型
        for(DicType d : dicTypeList){

            String dicType = d.getCode();

            //查询每个类型的所有值，并将其保存到Map中
            List<DicValue> dicValueList = dicValueDao.selectDicValueForType(dicType);
            map.put(dicType,dicValueList);
        }

        //将Map保存到上下文域对象中
        servletContext.setAttribute("dic",map);


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
