package com.wingscode.modules.common.service.impl;

import com.wingscode.modules.common.dao.LeadsDao;
import com.wingscode.modules.common.dao.UserTestDao;
import com.wingscode.modules.common.service.UserCountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author：张延
 * Date：2020-03-11 11:01
 * Description：<描述>
 */
@Service("UserCountService")
public class UserCountSericeImpl implements UserCountService {

    @Autowired
    private UserTestDao userTestDao;

    @Autowired
    private LeadsDao leadsDao;

    @Override
    public List leadersList(Map<String, Object> params) {

         String par= (String) params.get("parentId");
         Integer parentId =null;
         if(StringUtils.isNotEmpty(par) ){
             parentId=Integer.parseInt(par);
         }

        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");


        System.out.println(parentId+"_________");
        List list = userTestDao.queryUserList(parentId,date1,date2);
        System.out.println(list+"12321312");
        return list;
    }

    @Override
    public List orderAgentList(Map<String, Object> params) {
        List list = userTestDao.OrderAgentList(params);
        return  list;
    }
    //
    // @Override
    // public List zxLeadersList(Map<String, Object> params) {
    //     List list = userTestDao.queryZxLeadersList(params);
    //     return list;
    // }
    //
    // @Override
    // public List orderAgentList(Map<String, Object> params) {
    //     List list = userTestDao.OrderAgentList(params);
    //     return list;
    // }
    //
    // @Override
    // public List addAndShowLeadsList(Map<String, Object> params) {
    //
    //
    //     //获取当前时间
    //     Date nowDate = new Date();
    //
    //
    //     //获取当前十五天的日期
    //     String dayBefore = ModifyTimeUtil.getDayBefore(nowDate).toString();
    //
    //
    //
    //
    //
    //     List list = userTestDao.addAndShowLeadsList(params);
    //
    //
    //
    //     return list;
    // }


}
