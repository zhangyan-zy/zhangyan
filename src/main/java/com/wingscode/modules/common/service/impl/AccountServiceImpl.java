package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.Query;
import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.service.AccountService;
import com.wingscode.modules.common.service.LeadsService;
import com.wingscode.modules.sys.dao.SysUserDao;
import com.wingscode.modules.sys.entity.SysUserEntity;
import com.wingscode.modules.sys.entity.SysUserRoleEntity;
import com.wingscode.modules.sys.service.SysUserRoleService;
import com.wingscode.modules.sys.service.SysUserService;
import com.wingscode.util.MyUtilTime;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("accountService")
public class AccountServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements AccountService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private LeadsService leadsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        String parentId = (String) params.get("parentId");
        Long roleId = Long.parseLong(String.valueOf(params.get("roleId")));
        List<Long> ids = sysUserRoleService.queryUserIdList(roleId);
        IPage<SysUserEntity> page = this.page(
                new Query<SysUserEntity>().getPage(params),
                new QueryWrapper<SysUserEntity>()
                        .like(StringUtils.isNotEmpty(username), "username", username)
                        .in("user_id", ids)
                        .eq(StringUtils.isNotEmpty(parentId), "parent_id", parentId)
                        .eq(ids.size() == 0, "user_id", 0)
        );
        if (roleId == 5) {
            for (SysUserEntity sysUserEntity : page.getRecords()) {
                List<Long> roleIdList = sysUserRoleService.queryRoleIdList(sysUserEntity.getUserId());
                for (Long roleId_t : roleIdList) {
                    if (roleId_t == 6) {
                        sysUserEntity.setRolePower(1);
                    }
                }
                sysUserEntity.setRoleIdList(roleIdList);
                sysUserEntity.setParentName(sysUserService.getById(sysUserEntity.getParentId()).getUsername());
            }
        }
        return new PageUtils(page);
    }

    @Override
    public List<SysUserEntity> allList(Long roleId, Long parentId) {
        List<Long> ids = sysUserRoleService.queryUserIdList(roleId);
        return this.list(new QueryWrapper<SysUserEntity>()
                .in("user_id", ids)
                .eq(parentId != 0, "parent_id", parentId)
                .eq(ids.size() == 0, "user_id", 0));
    }

    @Override
    public void setWorkerAdmin(Long userId) {
        SysUserEntity sysUserEntity = sysUserService.getById(userId);
        List<Long> ids = sysUserService.queryUserIdList(sysUserEntity.getParentId());
        if (ids.size() > 0) {
            sysUserRoleService.remove(new QueryWrapper<SysUserRoleEntity>().eq("role_id", 6).in("user_id", ids));
        }
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setRoleId((long) 6);
        sysUserRoleEntity.setUserId(userId);
        sysUserRoleService.save(sysUserRoleEntity);
    }

    @Override
    public PageUtils allCustomer(Map<String, Object> params) {
        List<Long> ids = sysUserRoleService.queryUserIdList((long) 4);
        IPage<SysUserEntity> page = this.page(
                new Query<SysUserEntity>().getPage(params),
                new QueryWrapper<SysUserEntity>()
                        .in("user_id", ids)
        );
        page.getRecords().forEach(item -> {
            item.setAllNum(leadsService.count(new QueryWrapper<LeadsEntity>()
                    .eq("parent_id", item.getUserId())));
            item.setTodayNum(leadsService.count(new QueryWrapper<LeadsEntity>()
                    .eq("parent_id", item.getUserId())
                    .ge("gmt_creat", MyUtilTime.getDate(2, ""))
            ));
        });
        return new PageUtils(page);
    }

    @Override
    public PageUtils allWorker(Map<String, Object> params) {
        List<Long> ids = sysUserRoleService.queryUserIdList((long) 5);
        IPage<SysUserEntity> page = this.page(
                new Query<SysUserEntity>().getPage(params),
                new QueryWrapper<SysUserEntity>()
                        .in("user_id", ids)
        );
        page.getRecords().forEach(item -> {
            item.setAllNum(leadsService.count(new QueryWrapper<LeadsEntity>()
                    .eq("parent_id", item.getUserId())));
            item.setTodayNum(leadsService.count(new QueryWrapper<LeadsEntity>()
                    .eq("parent_id", item.getUserId())
                    .ge("gmt_creat", MyUtilTime.getDate(2, ""))
            ));
        });
        return new PageUtils(page);
    }

}
