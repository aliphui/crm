package com.atguigu.crm.setting.domain;

/**
 * @create 2021-12-14 21:49
 */
public class DicType {
    private String code;//主键，字典类型的英文，不能为中文
    private String description;//描述
    private String name;//字典类型

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
