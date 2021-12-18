package com.atguigu.crm.workbench.domain;

public class ContactsActivityRelation {

	private String id;
	private String contactsId;//联系人id
	private String activityId;//市场活动id
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContactsId() {
		return contactsId;
	}
	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	
	
}
