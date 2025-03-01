package com.wingscode.modules.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.modules.common.entity.AppuserEntity;
import com.wingscode.modules.common.service.AppuserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;




/**
 * 用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-12-26 22:57:56
 */
@RestController
@RequestMapping("generator/appuser")
public class AppuserController {
    @Autowired
    private AppuserService appuserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:appuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = appuserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:appuser:info")
    public R info(@PathVariable("id") Long id){
		AppuserEntity appuser = appuserService.getById(id);

        return R.ok().put("appuser", appuser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:appuser:save")
    public R save(@RequestBody AppuserEntity appuser){
        if (new BigDecimal(appuser.getAmount()).compareTo(new BigDecimal("0"))<0){
            return R.error("余额不能小于0");
        }
        appuser.setCreateTime(new Date());
		appuserService.save(appuser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:appuser:update")
    public R update(@RequestBody AppuserEntity appuser){
        if (appuser.getImageUrl().contains("token")){
            AppuserEntity byId = appuserService.getById(appuser.getId());
            appuser.setImageUrl(byId.getImageUrl());
        }
        if (new BigDecimal(appuser.getAmount()).compareTo(new BigDecimal("0"))<0){
            return R.error("余额不能小于0");
        }
		appuserService.updateById(appuser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:appuser:delete")
    public R delete(@RequestBody Long[] ids){
		appuserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 查询所有
     */
    @RequestMapping("/selectAll")
    public R selectAll(){
        List<AppuserEntity> appUserList =appuserService.list(
                new QueryWrapper<AppuserEntity>()
        );
        return R.ok().put("appUserList", appUserList);
    }

}
