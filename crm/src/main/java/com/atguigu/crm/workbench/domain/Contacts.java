package com.atguigu.crm.workbench.domain;

public class Contacts {
	
	private String id;//主键
	private String owner;//  外键——所有者
	private String source;//来源
	private String customerId;//  外键——客户id（公司）
	private String fullname;//全名（人的名字）
	private String appellation;	//称呼——先生、女士等等
	private String email;//邮箱
	private String mphone;//手机
	private String job;//职业
	private String birth;//生日

	private String createBy;//创建人
	private String createTime;//创建时间
	private String editBy;//修改人
	private String editTime;//修改时间

	private String description;//描述
	private String contactSummary;//联系纪要
	private String nextContactTime;//下次联系时间
	private String address;	//地址
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getAppellation() {
		return appellation;
	}
	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMphone() {
		return mphone;
	}
	public void setMphone(String mphone) {
		this.mphone = mphone;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getEditBy() {
		return editBy;
	}
	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}
	public String getEditTime() {
		return editTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContactSummary() {
		return contactSummary;
	}
	public void setContactSummary(String contactSummary) {
		this.contactSummary = contactSummary;
	}
	public String getNextContactTime() {
		return nextContactTime;
	}
	public void setNextContactTime(String nextContactTime) {
		this.nextContactTime = nextContactTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

	
}
