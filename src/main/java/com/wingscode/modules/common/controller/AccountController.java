package com.wingscode.modules.common.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.common.validator.ValidatorUtils;
import com.wingscode.common.validator.group.AddGroup;
import com.wingscode.common.validator.group.UpdateGroup;
import com.wingscode.modules.common.service.AccountService;
import com.wingscode.modules.sys.controller.AbstractController;
import com.wingscode.modules.sys.entity.SysUserEntity;
import com.wingscode.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author CrazyHuskar
 * @email jiangshengkang@wingscode.com
 * @date 2019-11-18 12:20:12
 */
@RestController
@RequestMapping("common/account")
public class AccountController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AccountService accountService;

    /**
     * 客户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("admin:account")
    public R coustomerList(@RequestParam Map<String, Object> params) {
        if (!StringUtils.isEmpty(params.get("parentId"))) {
            Long userId = Long.parseLong((String) params.get("parentId"));
            if (userId == -1) {
                params.put("parentId", getUserId() + "");
            }
        }
        PageUtils page = accountService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/coustomer")
    @RequiresPermissions("admin:coustomer")
    public R coustomerList() {
        return R.ok().put("user", accountService.allList((long) 4, (long) 0));
    }

    @RequestMapping("/staff")
    @RequiresPermissions("admin:staff")
    public R staffList() {
        return R.ok().put("user", accountService.allList((long) 7, getUserId()));
    }

    @RequestMapping("/worker")
    @RequiresPermissions("admin:account")
    public R workerList() {
        return R.ok().put("user", accountService.allList((long) 5, (long) 0));
    }

    @RequestMapping("/setWorkerAdmin")
    @RequiresPermissions("admin:worker")
    public R setWorkerAdmin(@RequestParam Long userId) {
        accountService.setWorkerAdmin(userId);
        return R.ok();
    }

    /**
     * 信息
     */
    @RequestMapping("/save")
    @RequiresPermissions("admin:account")
    public R coustomerSave(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        user.setCreateUserId(getUserId());
        if (user.getParentId() == -1) {
            user.setParentId(getUserId());
        }
        sysUserService.saveUser(user);
        return R.ok();
    }

    @RequestMapping("/update")
    @RequiresPermissions("admin:account")
    public R coustomerUpdate(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        user.setCreateUserId(getUserId());
        user.setSalt(sysUserService.getById(user.getUserId()).getSalt());
        sysUserService.update(user);
        sysUserService.update(new UpdateWrapper<SysUserEntity>().set("status", user.getStatus()).eq("parent_id", user.getUserId()));
        return R.ok();
    }



}
