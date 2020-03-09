package com.wingscode.modules.common.controller;


import com.wingscode.common.utils.R;
import com.wingscode.modules.common.service.AccountService;
import com.wingscode.modules.sys.service.SysUserService;

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
public class StatisticalController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/admin/allCustomer")
    @ApiOperation("所有客户的统计情况")
    public R allCustomer(@RequestParam Map<String, Object> params) {
        return R.ok().put("list", accountService.allCustomer(params));
    }
    @PostMapping("/admin/allWorker")
    @ApiOperation("所有坐席的统计情况")
    public R allWorker(@RequestParam Map<String, Object> params) {
        return R.ok().put("list", accountService.allWorker(params));
    }
}
