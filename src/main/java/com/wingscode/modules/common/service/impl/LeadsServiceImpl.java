package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.Query;
import com.wingscode.modules.common.dao.LeadsDao;
import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.service.LeadsService;
import com.wingscode.modules.sys.service.SysUserService;
import com.wingscode.util.MyTimeUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("leadsService")
public class LeadsServiceImpl extends ServiceImpl<LeadsDao, LeadsEntity> implements LeadsService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<Long> queryList(Long parentId, String name) {
        return baseMapper.queryList(parentId, name);
    }

    @Override
    public PageUtils listByStaff(Map<String, Object> params, Long parentId, Long staffId) {
        String username = (String) params.get("name");
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotBlank(username), "name", username)
                        .eq("parent_id", parentId)
                        .eq("dispose_user", staffId)
                        .orderByDesc("id")
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils listByWorker(Map<String, Object> params, Long parentId, Long workerId) {
        String username = (String) params.get("name");
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotBlank(username), "name", username)
                        .eq("parent_id", parentId)
                        .eq("worker_id", workerId)
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
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotBlank(username), "name", username)
                        .eq("parent_id", parentId)
                        .ge(amount1 != 0, "amount", amount1)
                        .le(amount2 != 0, "amount", amount2)
                        .ge(!StringUtils.isEmpty(date1), "gmt_creat", date1)
                        .le(!StringUtils.isEmpty(date2), "gmt_creat", MyTimeUtil.addDay(date2, 1))
                        .orderByDesc("id")
        );
        for (LeadsEntity leadsEntity : page.getRecords()) {
            leadsEntity.setDisposeUserName(sysUserService.getById(leadsEntity.getDisposeUser()).getUsername());
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
        IPage<LeadsEntity> page = this.page(
                new Query<LeadsEntity>().getPage(params),
                new QueryWrapper<LeadsEntity>()
                        .like(StringUtils.isNotBlank(username), "name", username)
                        .eq(parentId != 0, "parent_id", parentId)
                        .ge(amount1 != 0, "amount", amount1)
                        .le(amount2 != 0, "amount", amount2)
                        .ge(!StringUtils.isEmpty(date1), "gmt_creat", date1)
                        .le(!StringUtils.isEmpty(date2), "gmt_creat", MyTimeUtil.addDay(date2, 1))
                        .orderByDesc("id")
        );
        for (LeadsEntity leadsEntity : page.getRecords()) {
            leadsEntity.setDisposeUserName(sysUserService.getById(leadsEntity.getDisposeUser()).getUsername());
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

}
