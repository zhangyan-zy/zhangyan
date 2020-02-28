package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.Query;
import com.wingscode.modules.common.dao.LeadsDao;
import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.service.AccountService;
import com.wingscode.modules.common.service.LeadsService;
import com.wingscode.modules.sys.entity.SysUserEntity;
import com.wingscode.modules.sys.service.SysUserService;
import com.wingscode.util.MyTimeUtil;
import com.wingscode.util.MyUtilTime;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        String status = (String) params.get("status");
        int amount1 = Integer.valueOf(String.valueOf(params.get("amount1")));
        int amount2 = Integer.valueOf(String.valueOf(params.get("amount2")));
        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotEmpty(username), "name", username)
                        .eq("parent_id", parentId)
                        .eq("dispose_user", staffId)
                        .ge(amount1 != 0, "amount", amount1)
                        .le(amount2 != 0, "amount", amount2)
                        .ge(StringUtils.isNotEmpty(date1), "gmt_creat", date1)
                        .le(StringUtils.isNotEmpty(date2), "gmt_creat", MyTimeUtil.addDay(date2, 1))
                        .eq(StringUtils.isNotEmpty(status), "status", status)
                        .orderByDesc("id")
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils listByWorker(Map<String, Object> params, Long parentId, Long workerId) {
        String username = (String) params.get("name");
        String status = (String) params.get("status");
        int amount1 = Integer.valueOf(String.valueOf(params.get("amount1")));
        int amount2 = Integer.valueOf(String.valueOf(params.get("amount2")));
        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotEmpty(username), "name", username)
                        .eq("parent_id", parentId)
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
    public PageUtils listByCustomer(Map<String, Object> params, Long parentId) {
        String username = (String) params.get("name");
        int amount1 = Integer.valueOf(String.valueOf(params.get("amount1")));
        int amount2 = Integer.valueOf(String.valueOf(params.get("amount2")));
        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");
        String status = (String) params.get("status");
        String staff = (String) params.get("staff");
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotEmpty(username), "name", username)
                        .eq("parent_id", parentId)
                        .eq(StringUtils.isNotEmpty(status), "status", status)
                        .eq(StringUtils.isNotEmpty(staff), "dispose_user", staff)
                        .ge(amount1 != 0, "amount", amount1)
                        .le(amount2 != 0, "amount", amount2)
                        .ge(StringUtils.isNotEmpty(date1), "gmt_creat", date1)
                        .le(StringUtils.isNotEmpty(date2), "gmt_creat", MyTimeUtil.addDay(date2, 1))
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
        int amount1 = Integer.valueOf(String.valueOf(params.get("amount1")));
        int amount2 = Integer.valueOf(String.valueOf(params.get("amount2")));
        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");
        String status = (String) params.get("status");
        String workerId = (String) params.get("workerId");
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotEmpty(username), "name", username)
                        .eq(parentId != 0, "parent_id", parentId)
                        .eq(StringUtils.isNotEmpty(status), "status", status)
                        .eq(StringUtils.isNotEmpty(workerId), "worker_id", workerId)
                        .ge(amount1 != 0, "amount", amount1)
                        .le(amount2 != 0, "amount", amount2)
                        .ge(StringUtils.isNotEmpty(date1), "gmt_creat", date1)
                        .le(StringUtils.isNotEmpty(date2), "gmt_creat", MyTimeUtil.addDay(date2, 1))
                        .orderByDesc("id")
        );
        for (LeadsEntity leadsEntity : page.getRecords()) {
            if (leadsEntity.getDisposeUser() != 0) {
                leadsEntity.setDisposeUserName(sysUserService.getById(leadsEntity.getDisposeUser()).getUsername());
            }
            leadsEntity.setParentName(sysUserService.getById(leadsEntity.getParentId()).getUsername());
            leadsEntity.setWorkerName(sysUserService.getById(leadsEntity.getWorkerId()).getUsername());
        }
        return new PageUtils(page);
    }

    @Override
    public PageUtils waitList(Map<String, Object> params, Long parentId) {
        String username = (String) params.get("name");
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotBlank(username), "name", username)
                        .eq("parent_id", parentId)
                        .eq("status", 1)
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

}
