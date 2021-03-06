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

        //1?????????clueId????????????????????????
        int count1 = clueRemarkDao.deleteByClueId(id);
        if (count1 < 1){
            flag = false;
        }

        //2?????????clueId????????????????????????????????????????????????
        int count2 = clueActivityRelationDao.deleteByClueId(id);
        if (count2 < 1){
            flag = false;
        }

        //3?????????clueId???????????????
        int count3 = clueDao.deleteById(id);
        if (count3 != 1){
            flag = false;
        }

        return flag;
    }


    @Override
    public boolean converClue(String clueId, String createBy,Tran tran) {

        boolean flag = true;

        //??????????????????????????????????????????
        //??????clueId??????clue
        Clue clue = clueDao.selectClueById(clueId);

        //??????????????????????????????????????????????????????
        String company = clue.getCompany();
        Customer customer = customerDao.selectCustomerByName(company);
        if (customer == null){
            //???????????????????????????????????????
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

        //??????????????????????????????????????????
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());//?????????id
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

        //??????????????????????????? ????????? ???????????????????????????
        //??????clueId????????????????????????
        List<ClueRemark> clueRemarks = clueRemarkDao.selectClueRemarkByClueId(clueId);
        for(ClueRemark c: clueRemarks){
            //???????????????????????????
            String noteContent = c.getNoteContent();

            //?????????????????????????????????????????????
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
            //????????????????????????????????????????????????
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

        //??????????????????????????????????????????????????? ????????? ?????????????????????????????????
        //??????clueId???????????????????????????????????????????????????
        List<ClueActivityRelation> clueActivityRelation = clueActivityRelationDao.selectClueActivityRelationByClueId(clueId);
        for (ClueActivityRelation c: clueActivityRelation){
            //???????????????????????????????????????Id
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

        //????????????tran??????null??????????????????????????????
        if (tran != null){
            //????????????
            //???tran?????????????????????????????????money,name,expectedDate,stage,activityId???
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

            //??????????????????
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
        //?????????????????????????????????????????????????????????????????????
        if (flag){
            flag = i;
        }

        return flag;
    }

}
