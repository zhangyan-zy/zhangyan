package com.wingscode.modules.common.controller;

import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.modules.common.entity.OrderLogEntity;
import com.wingscode.modules.common.service.OrderLogService;
import com.wingscode.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;




/**
 * 订单日志
 *
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-14 17:36:42
 */
@RestController
@RequestMapping("generator/orderlog")
public class OrderLogController extends AbstractController {
    @Autowired
    private OrderLogService orderLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:orderlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:orderlog:info")
    public R info(@PathVariable("id") Integer id){
		OrderLogEntity orderLog = orderLogService.getById(id);

        return R.ok().put("orderLog", orderLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:orderlog:save")
    public R save(@RequestBody OrderLogEntity orderLog){
		orderLogService.save(orderLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:orderlog:update")
    public R update(@RequestBody OrderLogEntity orderLog){
		orderLogService.updateById(orderLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:orderlog:delete")
    public R delete(@RequestBody Integer[] ids){
		orderLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
