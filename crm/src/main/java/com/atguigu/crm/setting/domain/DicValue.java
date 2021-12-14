package com.atguigu.crm.setting.domain;

/**
 * @create 2021-12-14 21:49
 */
public class DicValue {
    private String id;//主键
    private String value;//字典值，不能为空，同一字典类型下的字典值不能重复
    private String text;
    private String orderNo;//用来排序
    private String typeCode;//字典类型，外键

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
