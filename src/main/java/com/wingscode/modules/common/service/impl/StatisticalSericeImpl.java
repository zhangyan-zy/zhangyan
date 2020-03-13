package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.wingscode.modules.app.entity.UserEntity;
import com.wingscode.modules.common.dao.LeadsDao;
import com.wingscode.modules.common.dao.StatisticalDao;
import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.service.StatisticalService;
import com.wingscode.modules.common.vo.CoustomerCountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Author：张延
 * Date：2020-03-11 11:01
 * Description：<描述>
 */
@Service("UserCountService")
public class StatisticalSericeImpl implements StatisticalService {

    @Autowired
    private StatisticalDao statisticalDao;

    @Autowired
    private LeadsDao leadsDao;



    @Override
    public Page<CoustomerCountEntity> selectCoustomerList(Map<String, Object> params) {

        // String param = (String) params.get("params");
        // Gson gson = new Gson();
        // Map<String, Object> map = new HashMap<String, Object>();
        // map = gson.fromJson(param, map.getClass());
        // System.out.println("111111111"+map);
        //
        //     Integer page=Integer.parseInt((String) map.get("page"));
        //     Integer limit=Integer.parseInt((String) map.get("limit"));
        //     Integer parentId=Integer.parseInt((String) map.get("parentId"));
        //     String date1= (String) map.get("date1");
        //     String date2= (String) map.get("date2");

       Integer page = Integer.parseInt((String)params.get("page"));
       Integer limit = Integer.parseInt((String)params.get("limit"));
        Integer parentId=Integer.parseInt((String)params.get("parentId"));
        String date1= (String) params.get("date1");
        String date2= (String) params.get("date2");

        System.out.println("121212"+date1);
        System.out.println("12121212"+date2);

       Page<CoustomerCountEntity> pages=new Page<>(page,limit);

       Page pagess=statisticalDao.selectCoustomerList(parentId,date1,date2,pages);

        return pagess;
    }

    @Override
    public Page<LeadsEntity> orderAgentList(Map<String, Object> params, Long parentId) {
        Integer page = Integer.parseInt((String)params.get("page"));
        Integer limit = Integer.parseInt((String)params.get("limit"));

        String date1= (String) params.get("date1");
        String date2= (String) params.get("date2");

        Page<UserEntity> pages=new Page<>(page,limit);

        Page pagess=statisticalDao.selectAgentList(parentId,date1,date2,pages);

        return pagess;
}


    // @Override
    // public List leadersList(Map<String, Object> params) {
    //
    //     String par = (String) params.get("parentId");
    //     Integer parentId = null;
    //     if (StringUtils.isNotEmpty(par)) {
    //         parentId = Integer.parseInt(par);
    //     }
    //     String date1 = (String) params.get("date1");
    //     String date2 = (String) params.get("date2");
    //     List list = userTestDao.queryUserList(parentId, date1, date2);
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
