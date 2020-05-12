package com.wingscode.modules.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.entity.LeadsLogEntity;
import com.wingscode.modules.common.entity.TraceEntity;
import com.wingscode.modules.common.service.LeadsLogService;
import com.wingscode.modules.common.service.LeadsService;
import com.wingscode.modules.common.vo.AdminProvinceCityVo;
import com.wingscode.modules.common.vo.AdminSysUserVo;
import com.wingscode.modules.sys.controller.AbstractController;
import com.wingscode.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author CrazyHuskar
 * @email jiangshengkang@wingscode.com
 * @date 2019-11-18 12:20:12
 */
@RestController
@RequestMapping("common/leads")
@Api("leads")
public class LeadsController extends AbstractController {
    @Autowired
    private LeadsService leadsService;
    @Autowired
    private LeadsLogService leadsLogService;
    @Autowired
    private SysUserService sysUserService;
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
        PageUtils page = leadsService.listByAdmin(params, Long.parseLong(StringUtils.isEmpty(params.get("parentId")) ? "0" : (String) params.get("parentId")));
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

    @RequestMapping("/waitCount")
    @RequiresPermissions("leads:list")
    public R waitCount() {
        return R.ok().put("count", leadsService.count(new QueryWrapper<LeadsEntity>()
                .eq("parent_id", getUserId())
                .eq("status", 1)));
    }

    @RequestMapping("/disposeCount")
    @RequiresPermissions("leads:list")
    public R disposeCount() {
        return R.ok().put("count", leadsService.count(new QueryWrapper<LeadsEntity>()
                .eq("dispose_user", getUserId())
                .eq("status", 2)));
    }

    @RequestMapping("/workerCount")
    @RequiresPermissions("leads:list")
    public R workerCount() {
        return R.ok().put("list", leadsService.workerCount(getUser()));
    }

    @RequestMapping("/toStaff")
    @RequiresPermissions("leads:update")
    public R toStaff(@RequestBody Map<String, Object> params) {
        List<Integer> ids = (List<Integer>) params.get("id");
        Long id = Long.valueOf(params.get("disposeUser").toString());
        ids.stream().forEach(item -> {
            LeadsEntity leadsEntity = leadsService.getById(item);
            LeadsLogEntity leadsLogEntity = new LeadsLogEntity();
            leadsLogEntity.setGmtCreat(new Date());
            leadsLogEntity.setGmtModified(new Date());
            leadsLogEntity.setDisposeUser(getUserId());
            leadsLogEntity.setLeadsId(Long.valueOf(item));
            leadsLogEntity.setRemark("分配");
            leadsLogEntity.setStatus(2);
            leadsLogEntity.setStatusOld(leadsEntity.getStatus());
            leadsLogService.save(leadsLogEntity);
            leadsEntity.setDisposeUser(id);
            leadsEntity.setStatus(2);
            leadsEntity.setGmtModified(new Date());
            leadsService.updateById(leadsEntity);
        });
        return R.ok();
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
    @ApiOperation("保存")
    public R save(@RequestBody LeadsEntity leadsEntity) {
        leadsEntity.setGmtCreat(new Date());
        leadsEntity.setGmtModified(new Date());
        leadsEntity.setStatus(1);
        leadsEntity.setParentId(getUser().getParentId());
        leadsEntity.setDisposeUser((long) 0);
        leadsEntity.setWorkerId(getUserId());
        leadsService.save(leadsEntity);
        leadsService.saveIphone(leadsEntity.getId());
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

    @RequestMapping("/delete")
    @RequiresPermissions("admin:leads:delete")
    public R delete(@RequestParam Long id) {
        leadsService.removeById(id);
        leadsLogService.remove(new QueryWrapper<LeadsLogEntity>().eq("leads_id", id));
        return R.ok();
    }


    @RequestMapping("/provinceCity")
    @RequiresPermissions("leads:list")
    public R provinceCity() {
        List<AdminProvinceCityVo> list= leadsService.selectProvinceCity();
        return R.ok().put("list",list);
    }

    @RequestMapping("/addTrace")
    @RequiresPermissions("leads:addTrace")
    public R saveTrace(@RequestBody TraceEntity traceEntity) {
        traceEntity.setGmtCreat(new Date());
        traceEntity.setGmtModified(new Date());
        leadsService.addTrace(traceEntity);
        return R.ok();
    }

    @RequestMapping("/traceList")
    @RequiresPermissions("leads:traceList")
    public R traceList(@RequestParam Map<String, Object> params) {
        PageUtils page = leadsService.selectTrace(params);
        return R.ok().put("page", page);
    }


    @RequestMapping("/listTraceByWorker")
    @RequiresPermissions("leads:list")
    public R listTraceByWorker(@RequestParam Map<String, Object> params) {
        PageUtils page = leadsService.listTraceByCustomer(params);
        return R.ok().put("page", page);
    }


    /**
     * 根据客户找到所有的leads
     */
    @PostMapping("/selectCustomerList")
    public R selectLeadsByCustomerId(@RequestParam Long customerId){
        List<LeadsEntity> list = leadsService.selectLeadsByCustomerId(customerId);
        return R.ok().put("list", list);
    }

    /**
     * 根据客户找到规定的leads
     */
    @PostMapping("/selectleadsrList")
    public R selectleadsrList(@RequestParam Map<String, Object> params){
        PageUtils page = leadsService.selectleadsrList(params);
        return R.ok().put("page", page);
    }


    /**
     * 所有客户列表s
     */
    @PostMapping("/selectCustomersList")
    @ApiOperation("所有客户列表")
    public R selectCustomerList(){
        List<AdminSysUserVo> list = sysUserService.selectCustomerList();
        return R.ok().put("list", list);
    }
}
