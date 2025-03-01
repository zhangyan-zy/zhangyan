package com.wingscode.modules.common.controller;

import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.modules.common.entity.StoreLogEntity;
import com.wingscode.modules.common.service.StoreLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 店铺公告
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-02-20 21:33:50
 */
@RestController
@RequestMapping("generator/storelog")
public class StoreLogController {
    @Autowired
    private StoreLogService storeLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:storelog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = storeLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:storelog:info")
    public R info(@PathVariable("id") Integer id){
		StoreLogEntity storeLog = storeLogService.getById(id);

        return R.ok().put("storeLog", storeLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:storelog:save")
    public R save(@RequestBody StoreLogEntity storeLog){
		storeLogService.save(storeLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:storelog:update")
    public R update(@RequestBody StoreLogEntity storeLog){
		storeLogService.updateById(storeLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:storelog:delete")
    public R delete(@RequestBody Integer[] ids){
		storeLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
