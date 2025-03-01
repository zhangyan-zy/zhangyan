package com.wingscode.modules.common.controller;

import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.modules.common.entity.AppuserCouponEntity;
import com.wingscode.modules.common.service.AppuserCouponService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-12-26 22:22:29
 */
@RestController
@RequestMapping("generator/appusercoupon")
public class AppuserCouponController {
    @Autowired
    private AppuserCouponService appuserCouponService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:appusercoupon:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = appuserCouponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:appusercoupon:info")
    public R info(@PathVariable("id") Integer id){
		AppuserCouponEntity appuserCoupon = appuserCouponService.getById(id);

        return R.ok().put("appuserCoupon", appuserCoupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:appusercoupon:save")
    public R save(@RequestBody AppuserCouponEntity appuserCoupon){
		appuserCouponService.save(appuserCoupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:appusercoupon:update")
    public R update(@RequestBody AppuserCouponEntity appuserCoupon){
		appuserCouponService.updateById(appuserCoupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:appusercoupon:delete")
    public R delete(@RequestBody Integer[] ids){
		appuserCouponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
