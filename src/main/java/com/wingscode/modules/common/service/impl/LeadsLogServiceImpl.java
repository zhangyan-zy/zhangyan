package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.Query;
import com.wingscode.modules.common.dao.LeadsLogDao;
import com.wingscode.modules.common.entity.LeadsLogEntity;
import com.wingscode.modules.common.service.LeadsLogService;
import com.wingscode.modules.common.service.LeadsService;
import com.wingscode.modules.sys.service.SysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("leadsLogService")
public class LeadsLogServiceImpl extends ServiceImpl<LeadsLogDao, LeadsLogEntity> implements LeadsLogService {
    @Autowired
    private LeadsService leadsService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long parentId) {
        List<Long> ids = leadsService.queryList(parentId, "%" + params.get("name") + "%");
        IPage<LeadsLogEntity> page = this.page(
                new Query<LeadsLogEntity>().getPage(params),
                new QueryWrapper<LeadsLogEntity>()
                        .eq(ids.size() == 0, "leads_id", 0)
                        .in("leads_id", ids)
                        .orderByDesc("id")
        );
        for (LeadsLogEntity leadsLogEntity : page.getRecords()) {
            leadsLogEntity.setLeadsName(leadsService.getById(leadsLogEntity.getLeadsId()).getName());
            leadsLogEntity.setDisposeUserName(sysUserService.getById(leadsLogEntity.getDisposeUser()).getUsername());
        }
        return new PageUtils(page);
    }

}
