package com.atguigu.crm.workbench.service.impl;

import com.atguigu.crm.setting.dao.UserDao;
import com.atguigu.crm.setting.domain.User;
import com.atguigu.crm.utils.DateTimeUtil;
import com.atguigu.crm.utils.UUIDUtil;
import com.atguigu.crm.workbench.dao.*;
import com.atguigu.crm.workbench.domain.*;
import com.atguigu.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @create 2021-12-14 11:36
 */
@Service
public class ClueServiceImpl  implements ClueService {

    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ClueRemarkDao clueRemarkDao;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao;

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerRemarkDao customerRemarkDao;

    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private ContactsRemarkDao contactsRemarkDao;
    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;

    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;


    @Autowired
    private UserDao userDao;

    @Autowired
    private ActivityDao activityDao;

    @Override
    public List<User> getUserList() {
        List<User> userList = userDao.selectUserList();
        return userList;
    }

    @Override
    public boolean createClue(Clue clue) {
        int count = clueDao.createClue(clue);

        if (count != 1){
            return false;
        }
        return true;
    }

    @Override
    public List<Clue> selectClueList(Clue clue) {
        List<Clue> clues = clueDao.selectClueList(clue);
        return clues;
    }

    @Override
    public Clue selectClueById(String id) {
        return clueDao.selectClueAndUserByClueId(id);
    }

    @Override
    public List<Activity>  selectActivityByClueId(String clueId,String aname) {

        return activityDao.selectActivityByClueId(clueId,aname);
    }

    @Override
    public boolean deleteRelationByCARId(String carId) {
        int count  = clueActivityRelationDao.deleteById(carId);
        if (count != 1){
            return false;
        }
        return true;
    }

    @Override
    public List<Activity> selectActivityByName(String clueId, String aname) {

        return activityDao.selectActivityByName(clueId,aname);
    }

    @Override
    public boolean createClueAndActivitys(List<ClueActivityRelation> list) {

        int count = clueActivityRelationDao.createClueAndActivitys(list);
        if (count < 1){
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteClueById(String id) {

        boolean flag = true;

        //1、根据clueId删除所有线索备注
        int count1 = clueRemarkDao.deleteByClueId(id);
        if (count1 < 1){
            flag = false;
        }

        //2、根据clueId删除所有线索与市场活动关联关系，
        int count2 = clueActivityRelationDao.deleteByClueId(id);
        if (count2 < 1){
            flag = false;
        }

        //3、根据clueId删除线索，
        int count3 = clueDao.deleteById(id);
        if (count3 != 1){
            flag = false;
        }

        return flag;
    }


    @Override
    public boolean converClue(String clueId, String createBy,Tran tran) {

        boolean flag = true;

        //一、将线索转换为客户和联系人
        //根据clueId查询clue
        Clue clue = clueDao.selectClueById(clueId);

        //判断客户是否已存在，不存在时新建客户
        String company = clue.getCompany();
        Customer customer = customerDao.selectCustomerByName(company);
        if (customer == null){
            //创建客户对象，设置对象信息
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(clue.getOwner());
            customer.setName(company);
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setCreateBy(createBy);
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(clue.getContactSummary());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setDescription(clue.getDescription());
            customer.setAddress(clue.getAddress());
            int count1 = customerDao.createCustomer(customer);
            if (count1 != 1){
                flag = false;
            }
        }

        //创建联系人对象，设置对象信息
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());//联系人id
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(clue.getCreateTime());
        contacts.setDescription(clue.getDescription());
        contacts.setAddress(clue.getAddress());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        int count2 = contactsDao.createContacts(contacts);
        if (count2 != 1){
            flag = false;
        }

        //二、将所有线索备注 转换为 客户和联系人的备注
        //根据clueId查询所有线索备注
        List<ClueRemark> clueRemarks = clueRemarkDao.selectClueRemarkByClueId(clueId);
        for(ClueRemark c: clueRemarks){
            //获取线索备注的内容
            String noteContent = c.getNoteContent();

            //创建客户备注对象，设置对象信息
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(noteContent);
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(DateTimeUtil.getSysTime());
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            int count3 = customerRemarkDao.createCustomerRemark(customerRemark);
            if (count3 != 1){
                flag = false;
            }
            //创建联系人备注对象，设置对象信息
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(contacts.getId());
            int count4 = contactsRemarkDao.createContactsRemark(contactsRemark);
            if (count4 != 1){
                flag = false;
            }
        }

        //三、将所有线索与市场活动的关联关系 转换为 联系人与市场活动的关系
        //根据clueId查找所有此线索与市场活动的关联关系
        List<ClueActivityRelation> clueActivityRelation = clueActivityRelationDao.selectClueActivityRelationByClueId(clueId);
        for (ClueActivityRelation c: clueActivityRelation){
            //获取与线索关联的市场活动的Id
            String activityId = c.getActivityId();

            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());
            int count5 = contactsActivityRelationDao.createContactsActivityRelation(contactsActivityRelation);
            if (count5 != 1){
                flag = false;
            }
        }

        //四、如果tran不为null，创建交易和交易历史
        if (tran != null){
            //创建交易
            //为tran设置属性值（已经封装：money,name,expectedDate,stage,activityId）
            tran.setId(UUIDUtil.getUUID());
            tran.setOwner(clue.getOwner());
            tran.setSource(clue.getSource());
            tran.setCreateBy(createBy);
            tran.setCreateTime(DateTimeUtil.getSysTime());
            tran.setContactsId(contacts.getId());
            tran.setCustomerId(customer.getId());
            int count6 = tranDao.createTran(tran);
            if (count6 != 1){
                flag = false;
            }

            //创建交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setStage(tran.getStage());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateTime(DateTimeUtil.getSysTime());
            tranHistory.setCreateBy(createBy);
            tranHistory.setTranId(tran.getId());
            int count7 = tranHistoryDao.createTranHistory(tranHistory);
            if (count7 != 1){
                flag = false;
            }

        }

        boolean i = deleteClueById(clueId);
        //五、删除线索、线索备注、线索与市场活动关联关系
        if (flag){
            flag = i;
        }

        return flag;
    }

}
