package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.Query;
import com.wingscode.modules.common.dao.LeadsDao;
import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.entity.TraceEntity;
import com.wingscode.modules.common.service.AccountService;
import com.wingscode.modules.common.service.LeadsService;
import com.wingscode.modules.common.vo.AdminProvinceCityVo;
import com.wingscode.modules.sys.entity.SysUserEntity;
import com.wingscode.modules.sys.service.SysUserService;
import com.wingscode.util.MyTimeUtil;
import com.wingscode.util.MyUtilTime;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("leadsService")
public class LeadsServiceImpl extends ServiceImpl<LeadsDao, LeadsEntity> implements LeadsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AccountService accountService;

    @Override
    public List<Long> queryList(Long parentId, String name) {
        return baseMapper.queryList(parentId, name);
    }

    @Override
    public PageUtils listByStaff(Map<String, Object> params, Long parentId, Long staffId) {
        String username = (String) params.get("name");
        String mobile = (String) params.get("mobile");
        String status = (String) params.get("status");
        int amount1 = Integer.valueOf(String.valueOf(params.get("amount1")));
        int amount2 = Integer.valueOf(String.valueOf(params.get("amount2")));
        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");
        String balanceDate1 = (String) params.get("balanceDate1");
        String balanceDate2 = (String) params.get("balanceDate2");
        String need = (String) params.get("need");
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotEmpty(username), "name", username)
                        .like(StringUtils.isNotEmpty(mobile), "phone", mobile)
                        .eq("parent_id", parentId)
                        .eq("dispose_user", staffId)
                        .ge(amount1 != 0, "amount", amount1)
                        .le(amount2 != 0, "amount", amount2)
                        .ge(StringUtils.isNotEmpty(date1), "gmt_creat", date1)
                        .le(StringUtils.isNotEmpty(date2), "gmt_creat", MyTimeUtil.addDay(date2, 1))
                        .ge(StringUtils.isNotEmpty(balanceDate1), "balance_time", balanceDate1)
                        .le(StringUtils.isNotEmpty(balanceDate2), "balance_time", MyTimeUtil.addDay(balanceDate2, 1))
                        .eq(StringUtils.isNotEmpty(status), "status", status)
                        .eq(StringUtils.isNotEmpty(need), "need", need)
                        .orderByDesc("id")
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils listByWorker(Map<String, Object> params, Long parentId, Long workerId) {
        String username = (String) params.get("name");
        String mobile = (String) params.get("mobile");
        String status = (String) params.get("status");
        int amount1 = Integer.valueOf(String.valueOf(params.get("amount1")));
        int amount2 = Integer.valueOf(String.valueOf(params.get("amount2")));
        String balanceDate1 = (String) params.get("balanceDate1");
        String balanceDate2 = (String) params.get("balanceDate2");
        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotEmpty(username), "name", username)
                        .like(StringUtils.isNotEmpty(mobile), "phone", mobile)
                        .eq("parent_id", parentId)
                        .ge(StringUtils.isNotEmpty(balanceDate1), "balance_time", balanceDate1)
                        .le(StringUtils.isNotEmpty(balanceDate2), "balance_time", MyTimeUtil.addDay(balanceDate2, 1))
                        .ge(amount1 != 0, "amount", amount1)
                        .le(amount2 != 0, "amount", amount2)
                        .eq(StringUtils.isNotEmpty(status), "status", status)
                        .eq("worker_id", workerId)
                        .ge(StringUtils.isNotEmpty(date1), "gmt_creat", date1)
                        .le(StringUtils.isNotEmpty(date2), "gmt_creat", MyTimeUtil.addDay(date2, 1))
                        .orderByDesc("id")
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils listTraceByCustomer(Map<String, Object> params) {
        Integer page = Integer.parseInt((String) params.get("page"));
        Integer limit = Integer.parseInt((String) params.get("limit"));
        String username =null;
        if(!StringUtil.isNullOrEmpty((String) params.get("name"))){
            username = (String) params.get("name");
        }
        String mobile =null;
        if(!StringUtil.isNullOrEmpty((String) params.get("mobile"))){
            mobile = (String) params.get("mobile");
        }
        String status =null;
        if(!StringUtil.isNullOrEmpty((String) params.get("status"))){
            status = (String) params.get("status");
        }
        Integer amount1 =null;
        if(!StringUtil.isNullOrEmpty((String) params.get("amount1"))){
            amount1 = Integer.parseInt((String) params.get("amount1"));
        }
        Integer amount2 =null;
        if(!StringUtil.isNullOrEmpty((String) params.get("amount2"))){
            amount2 = Integer.parseInt((String) params.get("amount2"));
        }
        String date1 =null;
        if(!StringUtil.isNullOrEmpty((String) params.get("date1"))){
            date1 = (String) params.get("date1");
        }
        String date2 =null;
        if(!StringUtil.isNullOrEmpty((String) params.get("date2"))){
            date2 = (String) params.get("date2");
            date2= MyTimeUtil.addDay(date2, 1);
        }
        Page<TraceEntity> pages = new Page<>(page, limit);
        return new PageUtils(baseMapper.selectTraceByCustomer(pages,username,mobile,status,amount1,amount2,date1,date2));
    }


    @Override
    public PageUtils listByCustomer(Map<String, Object> params, Long parentId) {
        String username = (String) params.get("name");
        String mobile = (String) params.get("mobile");
        int amount1 = Integer.valueOf(String.valueOf(params.get("amount1")));
        int amount2 = Integer.valueOf(String.valueOf(params.get("amount2")));
        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");
        String balanceDate1 = (String) params.get("balanceDate1");
        String balanceDate2 = (String) params.get("balanceDate2");
        String status = (String) params.get("status");
        String staff = (String) params.get("staff");
        String need = (String) params.get("need");
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotEmpty(username), "name", username)
                        .like(StringUtils.isNotEmpty(mobile), "phone", mobile)
                        .eq("parent_id", parentId)
                        .eq(StringUtils.isNotEmpty(status), "status", status)
                        .eq(StringUtils.isNotEmpty(staff), "dispose_user", staff)
                        .ge(amount1 != 0, "amount", amount1)
                        .le(amount2 != 0, "amount", amount2)
                        .ge(StringUtils.isNotEmpty(balanceDate1), "balance_time", balanceDate1)
                        .le(StringUtils.isNotEmpty(balanceDate2), "balance_time", MyTimeUtil.addDay(balanceDate2, 1))
                        .ge(StringUtils.isNotEmpty(date1), "gmt_creat", date1)
                        .le(StringUtils.isNotEmpty(date2), "gmt_creat", MyTimeUtil.addDay(date2, 1))
                        .eq(StringUtils.isNotEmpty(need), "need", need)
                        .orderByDesc("id")
        );
        for (LeadsEntity leadsEntity : page.getRecords()) {
            if (leadsEntity.getDisposeUser() != 0) {
                leadsEntity.setDisposeUserName(sysUserService.getById(leadsEntity.getDisposeUser()).getUsername());
            }
        }
        return new PageUtils(page);
    }

    @Override
    public PageUtils listByAdmin(Map<String, Object> params, Long parentId) {
        String username = (String) params.get("name");
        String mobile = (String) params.get("mobile");
        int amount1 = Integer.valueOf(String.valueOf(params.get("amount1")));
        int amount2 = Integer.valueOf(String.valueOf(params.get("amount2")));
        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");
        String balanceDate1 = (String) params.get("balanceDate1");
        String balanceDate2 = (String) params.get("balanceDate2");
        String status = (String) params.get("status");
        String workerId = (String) params.get("workerId");
        String staff = (String) params.get("staff");
        String province = (String) params.get("province");
        String city = (String) params.get("city");
        String status1 = (String) params.get("status1");
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotEmpty(username), "name", username)
                        .like(StringUtils.isNotEmpty(mobile), "phone", mobile)
                        .eq(parentId != 0, "parent_id", parentId)
                        .eq(StringUtils.isNotEmpty(status), "status", status)
                        .eq(StringUtils.isNotEmpty(workerId), "worker_id", workerId)
                        .eq(StringUtils.isNotEmpty(staff), "dispose_user", staff)
                        .ge(amount1 != 0, "amount", amount1)
                        .le(amount2 != 0, "amount", amount2)
                        .ge(StringUtils.isNotEmpty(date1), "gmt_creat", date1)
                        .le(StringUtils.isNotEmpty(date2), "gmt_creat", MyTimeUtil.addDay(date2, 1))
                        .ge(StringUtils.isNotEmpty(balanceDate1), "balance_time", balanceDate1)
                        .le(StringUtils.isNotEmpty(balanceDate2), "balance_time", MyTimeUtil.addDay(balanceDate2, 1))
                        .orderByDesc("id")
                        .eq(StringUtils.isNotEmpty(province),"province",province)
                        .eq(StringUtils.isNotEmpty(city),"city",city)
                        .eq(StringUtils.isNotEmpty(status1), "status1", status1)
        );
           for (LeadsEntity leadsEntity : page.getRecords()) {
            if (leadsEntity.getDisposeUser() != 0) {
                leadsEntity.setDisposeUserName(sysUserService.getById(leadsEntity.getDisposeUser()).getUsername());
            }
            if (leadsEntity.getParentId() != 0) {
            leadsEntity.setParentName(sysUserService.getById(leadsEntity.getParentId()).getUsername());
            }
            if (leadsEntity.getWorkerId() != 0) {
            leadsEntity.setWorkerName(sysUserService.getById(leadsEntity.getWorkerId()).getUsername());
            }

        }
        return new PageUtils(page);
    }

    @Override
    public PageUtils waitList(Map<String, Object> params, Long parentId) {
        String username = (String) params.get("name");
        String need = (String) params.get("need");
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotBlank(username), "name", username)
                        .eq("parent_id", parentId)
                        .eq("status", 1)
                        .eq(StringUtils.isNotEmpty(need),"need",need)
                        .orderByDesc("id")
        );
        return new PageUtils(page);
    }

    @Override
    public List<Map<String, Object>> workerCount(SysUserEntity worker) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<SysUserEntity> sysUserEntity = accountService.allList((long) 5, worker.getParentId());
        sysUserEntity.stream().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", item.getUsername());
            map.put("num", this.count(new QueryWrapper<LeadsEntity>()
                    .ge("gmt_creat", MyUtilTime.getDate(2, ""))
                    .eq("worker_id", item.getUserId())
                    .eq("parent_id", item.getParentId())));
            mapList.add(map);
        });
        Collections.sort(mapList, (o1, o2) -> {
            int t1 = (int) o1.get("num");
            int t2 = (int) o2.get("num");
            if (t1 > t2) {
                return -1;
            } else if (t1 < t2) {
                return 1;
            } else {
                return 0;
            }
        });
        return mapList;
    }

    @Override
    public void saveIphone(Long leadsId) {
           baseMapper.addiphone(leadsId);
    }

    @Override
    public List<AdminProvinceCityVo>  selectProvinceCity() {

        List<AdminProvinceCityVo> list=new ArrayList<>();

           //查询所有省份
           List<String> list1= baseMapper.selectProvince();

            for(int i=0;i<list1.size();i++){
                AdminProvinceCityVo adminProvinceCityVo=new AdminProvinceCityVo();
                //查询省份城市
                    List list3 = baseMapper.selectCity(list1.get(i));
                    adminProvinceCityVo.setProvince(list1.get(i));
                    adminProvinceCityVo.setCity(list3);
                    list.add(adminProvinceCityVo);
         }

        return list;
    }

    @Override
    public List<LeadsEntity> listByAdminExcel(Map<String, Object> params, Long parentId) {
        String username = (String) params.get("name");
        String mobile = (String) params.get("mobile");
        int amount1 = Integer.valueOf(String.valueOf(params.get("amount1")));
        int amount2 = Integer.valueOf(String.valueOf(params.get("amount2")));
        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");
        String status = (String) params.get("status");
        String workerId = (String) params.get("workerId");
        String staff = (String) params.get("staff");
        String province = (String) params.get("province");
        String city = (String) params.get("city");
        List<LeadsEntity> leadsList =this.list(
                       new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotEmpty(username), "name", username)
                        .like(StringUtils.isNotEmpty(mobile), "phone", mobile)
                        .eq(parentId != 0, "parent_id", parentId)
                        .eq(StringUtils.isNotEmpty(status), "status", status)
                        .eq(StringUtils.isNotEmpty(workerId), "worker_id", workerId)
                        .eq(StringUtils.isNotEmpty(staff), "dispose_user", staff)
                        .ge(amount1 != 0, "amount", amount1)
                        .le(amount2 != 0, "amount", amount2)
                        .ge(StringUtils.isNotEmpty(date1), "gmt_creat", date1)
                        .le(StringUtils.isNotEmpty(date2), "gmt_creat", MyTimeUtil.addDay(date2, 1))
                        .orderByDesc("id")
                        .eq(StringUtils.isNotEmpty(province),"province",province)
                        .eq(StringUtils.isNotEmpty(city),"city",city)
        );
        for (LeadsEntity leadsEntity :leadsList) {
            if (leadsEntity.getDisposeUser() != 0) {
                leadsEntity.setDisposeUserName(sysUserService.getById(leadsEntity.getDisposeUser()).getUsername());
            }
            if (leadsEntity.getParentId() != 0) {
                leadsEntity.setParentName(sysUserService.getById(leadsEntity.getParentId()).getUsername());
            }
            if (leadsEntity.getWorkerId() != 0) {
                leadsEntity.setWorkerName(sysUserService.getById(leadsEntity.getWorkerId()).getUsername());
            }

        }
        return leadsList;
    }

    @Override
    public void addTrace(TraceEntity traceEntity) {
          baseMapper.addTracs(traceEntity);
    }

    @Override
    public PageUtils selectTrace(Map<String, Object> params) {
        Integer page = Integer.parseInt((String) params.get("page"));
        Integer limit = Integer.parseInt((String) params.get("limit"));
        Integer leadsId=null;
        if(!StringUtil.isNullOrEmpty((String) params.get("id"))){
            leadsId =Integer.parseInt((String) params.get("id"));
        }
        Page<TraceEntity> pages = new Page<>(page, limit);
        return new PageUtils(baseMapper.selectTrace(pages,leadsId));

    }

    @Override
    public List<LeadsEntity> selectLeadsByCustomerId(Long customerId) {
        return baseMapper.selectLeadsByCustomerId(customerId);
    }

    @Override
    public PageUtils selectleadsrList(Map<String, Object> params) {
        Integer page = Integer.parseInt((String) params.get("page"));
        Integer limit = Integer.parseInt((String) params.get("limit"));
        Long customerId=null;
        if(!StringUtil.isNullOrEmpty((String) params.get("customerId"))){
            customerId =new Long((String) params.get("customerId"));
        }
        Page<LeadsEntity> pages = new Page<>(page, limit);
        return new PageUtils(baseMapper.selectleadsrList(pages,customerId));
    }

    @Override
    public void updateLeadsStatusByLeadsId(Long leadsId) {
        baseMapper.updateLeadsStatusByLeadsId(leadsId);
    }

}
