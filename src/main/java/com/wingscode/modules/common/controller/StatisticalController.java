package com.wingscode.modules.common.controller;


import com.wingscode.common.utils.R;
import com.wingscode.modules.common.service.AccountService;
import com.wingscode.modules.common.service.StatisticalService;
import com.wingscode.modules.sys.controller.AbstractController;
import com.wingscode.modules.sys.service.SysUserService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * APP测试接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/common")
@Api("统计接口")
public class StatisticalController  extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StatisticalService statisticalService;

    @PostMapping("/admin/allCustomer")
    @ApiOperation("所有客户的统计情况")
    public R allCustomer(@RequestParam Map<String, Object> params) {
        return R.ok().put("list",statisticalService.selectCustomerList(params));
    }
    @PostMapping("/admin/allWorker")
    @ApiOperation("所有坐席的统计情况")
    public R allWorker(@RequestParam Map<String, Object> params) {
        return R.ok().put("list", statisticalService.allWorker(params));
    }


    //客户leads查询
    @PostMapping("/coustomerLeadersList")
    @RequiresPermissions("admin:account")
    public R CoustomerList(@RequestParam Map<String, Object> params) {
        return R.ok().put("user", statisticalService.selectCustomerList(params));
    }

    //坐席排序
    @PostMapping("/AgentsLeadersList")
    @RequiresPermissions("admin:account")
    public R AgentList(@RequestParam  Map<String, Object> params) {
        return R.ok().put("user", statisticalService.selectAgentList(params));
    }


}
