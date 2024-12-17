package com.wingscode.modules.common.controller;

import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.modules.common.entity.OrdersEntity;
import com.wingscode.modules.common.service.OrdersService;
import com.wingscode.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


/**
 * 订单表
 *
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-14 17:36:42
 */
@RestController
@RequestMapping("generator/orders")
public class OrdersController extends AbstractController {
    @Autowired
    private OrdersService ordersService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:orders:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ordersService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{orderNo}")
    @RequiresPermissions("generator:orders:info")
    public R info(@PathVariable("orderNo") String orderNo){
		OrdersEntity orders = ordersService.getById(orderNo);

        return R.ok().put("orders", orders);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:orders:save")
    public R save(@RequestBody OrdersEntity orders){
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 18);
        System.out.println(uuid);
        orders.setOrderNo(uuid);
        orders.setCreateTime(new Date());
        orders.setUpdateTime(new Date());
		ordersService.save(orders);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:orders:update")
    public R update(@RequestBody OrdersEntity orders){
        orders.setUpdateTime(new Date());
        ordersService.updateById(orders);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:orders:delete")
    public R delete(@RequestBody String[] orderNos){
		ordersService.removeByIds(Arrays.asList(orderNos));

        return R.ok();
    }

}
