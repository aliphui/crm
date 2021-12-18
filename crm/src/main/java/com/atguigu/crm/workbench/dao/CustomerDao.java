package com.atguigu.crm.workbench.dao;

import com.atguigu.crm.workbench.domain.Customer;
import org.apache.ibatis.annotations.Param;

public interface CustomerDao {

    /**
     * 通过名称（公司名）查询客户
     * @param company
     * @return
     */
    Customer selectCustomerByName(@Param("company") String company);

    /**
     * 创建客户
     * @param customer
     * @return
     */
    int createCustomer(Customer customer);
}
