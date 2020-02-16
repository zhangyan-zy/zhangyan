package com.wingscode.modules.common.controller;

import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.entity.LeadsLogEntity;
import com.wingscode.modules.common.service.LeadsLogService;
import com.wingscode.modules.common.service.LeadsService;
import com.wingscode.modules.sys.controller.AbstractController;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;


/**
 * @author CrazyHuskar
 * @email jiangshengkang@wingscode.com
 * @date 2019-11-18 12:20:12
 */
@RestController
@RequestMapping("common/leads")
public class LeadsController extends AbstractController {
    @Autowired
    private LeadsService leadsService;
    @Autowired
    private LeadsLogService leadsLogService;

    /**
     * 列表
     */
    @RequestMapping("/listByStaff")
    @RequiresPermissions("leads:list")
    public R listByStaff(@RequestParam Map<String, Object> params) {
        PageUtils page = leadsService.listByStaff(params, getUser().getParentId(), getUserId());
        return R.ok().put("page", page);
    }

    @RequestMapping("/listByWorker")
    @RequiresPermissions("leads:list")
    public R listByWorker(@RequestParam Map<String, Object> params) {
        PageUtils page = leadsService.listByWorker(params, getUser().getParentId(), getUserId());
        return R.ok().put("page", page);
    }

    @RequestMapping("/listByCustomer")
    @RequiresPermissions("leads:list")
    public R listByCustomer(@RequestParam Map<String, Object> params) {
        PageUtils page = leadsService.listByCustomer(params, getUserId());
        return R.ok().put("page", page);
    }

    @RequestMapping("/listByAdmin")
    @RequiresPermissions("leads:list")
    public R listByAdmin(@RequestParam Map<String, Object> params) {
        PageUtils page = leadsService.listByAdmin(params, Long.parseLong((String) params.get("parentId")));
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/waitList")
    @RequiresPermissions("leads:list")
    public R waitList(@RequestParam Map<String, Object> params) {
        PageUtils page = leadsService.waitList(params, getUserId());
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("leads:info")
    public R info(@PathVariable("id") Long id) {
        LeadsEntity leadsEntity = leadsService.getById(id);

        return R.ok().put("leads", leadsEntity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("leads:save")
    public R save(@RequestBody LeadsEntity leadsEntity) {
        leadsEntity.setGmtCreat(new Date());
        leadsEntity.setGmtModified(new Date());
        leadsEntity.setStatus(1);
        leadsEntity.setParentId(getUser().getParentId());
        leadsEntity.setDisposeUser((long) 0);
        leadsEntity.setWorkerId(getUserId());
        leadsService.save(leadsEntity);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("leads:update")
    public R update(@RequestBody LeadsEntity leadsEntity) {
        LeadsEntity leadsEntity_old = leadsService.getById(leadsEntity.getId());
        if (!StringUtils.isEmpty(leadsEntity.getStatus()) && !leadsEntity.getStatus().equals(leadsEntity_old.getStatus())) {
            LeadsLogEntity leadsLogEntity = new LeadsLogEntity();
            leadsLogEntity.setGmtCreat(new Date());
            leadsLogEntity.setGmtModified(new Date());
            leadsLogEntity.setDisposeUser(getUserId());
            leadsLogEntity.setLeadsId(leadsEntity.getId());
            leadsLogEntity.setRemark(leadsEntity.getStatusRemark());
            leadsLogEntity.setStatus(leadsEntity.getStatus());
            leadsLogEntity.setStatusOld(leadsEntity_old.getStatus());
            leadsLogService.save(leadsLogEntity);
        }
        if (leadsEntity.getStatus() == 3) {
            leadsEntity.setFriends(1);
        }
        leadsEntity.setGmtModified(new Date());
        leadsEntity.setParentId(leadsEntity_old.getParentId());
        leadsEntity.setWorkerId(leadsEntity_old.getWorkerId());
        leadsService.updateById(leadsEntity);
        return R.ok();
    }

    @RequestMapping("/log")
    @RequiresPermissions("leadsLog:list")
    public R log(@RequestParam Map<String, Object> params) {
        PageUtils page = leadsLogService.queryPage(params, getUserId());
        return R.ok().put("page", page);
    }
}
