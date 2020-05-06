package com.wingscode.modules.common.controller;

import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.common.validator.ValidatorUtils;
import com.wingscode.modules.common.entity.BillOutEntity;
import com.wingscode.modules.common.service.BillOutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;



/**
 * 账单表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
@RestController
@RequestMapping("sys/billout")
@Api("成本")
public class BillOutController {
    @Autowired
    private BillOutService billOutService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:billout:list")
    @ApiOperation("列表")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = billOutService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:billout:info")
    @ApiOperation("信息")
    public R info(@PathVariable("id") Long id){
        BillOutEntity billOut = billOutService.getById(id);

        return R.ok().put("billOut", billOut);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:billout:save")
    @ApiOperation("保存")
    public R save(@RequestBody BillOutEntity billOut){
        billOut.setGmtCreate(new Date());
        billOut.setGmtModify(new Date());
        Integer num = billOut.getNum();
        BigDecimal num1 = BigDecimal.valueOf(num.intValue());
        BigDecimal cost1 = billOut.getCost1();
        BigDecimal numCost=num1.multiply(cost1);
        BigDecimal cost2 = billOut.getCost2();
        BigDecimal cost3 = billOut.getCost3();
        BigDecimal add = numCost.add(cost2);
        BigDecimal add1 = add.add(cost3);
        billOut.setAllCost(add1);
        billOut.setBillDate(new Date());
        billOutService.save(billOut);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:billout:update")
    @ApiOperation("修改")
    public R update(@RequestBody BillOutEntity billOut){
        billOut.setGmtModify(new Date());
        ValidatorUtils.validateEntity(billOut);
        Integer num = billOut.getNum();
        BigDecimal num1 = BigDecimal.valueOf(num.intValue());
        BigDecimal cost1 = billOut.getCost1();
        BigDecimal numCost=num1.multiply(cost1);
        BigDecimal cost2 = billOut.getCost2();
        BigDecimal cost3 = billOut.getCost3();
        BigDecimal add = numCost.add(cost2);
        BigDecimal add1 = add.add(cost3);
        billOut.setAllCost(add1);
        billOut.setBillDate(new Date());
        billOutService.updateById(billOut);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:billout:delete")
    @ApiOperation("删除")

    public R delete(@RequestBody Long[] ids){
        billOutService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
