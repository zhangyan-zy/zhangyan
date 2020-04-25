package com.wingscode.modules.common.controller;

import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.common.validator.ValidatorUtils;
import com.wingscode.modules.common.dao.BillInDao;
import com.wingscode.modules.common.entity.BillInEntity;
import com.wingscode.modules.common.entity.BillInInfoEntity;
import com.wingscode.modules.common.service.BillInInfoService;
import com.wingscode.modules.common.service.BillInService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;




/**
 * 账单详单
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
@RestController
@RequestMapping("common/billininfo")
public class BillInInfoController {
    @Autowired
    private BillInInfoService billInInfoService;
    @Resource
    private BillInDao billInDao;

    @Autowired
    private BillInService billInService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:billininfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = billInInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:billininfo:info")
    public R info(@PathVariable("id") Long id){
        BillInInfoEntity billInInfo = billInInfoService.getById(id);

        return R.ok().put("billInInfo", billInInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:billininfo:save")
    public R save(@RequestParam Map<String, Object> params){
        BillInInfoEntity billInInfo=new BillInInfoEntity();
        billInInfo.setAmount(new BigDecimal(String.valueOf(params.get("amount"))));
        billInInfo.setBillId(new Long(String.valueOf(params.get("billId"))) );
        billInInfo.setGmtCreate(new Date());
        billInInfo.setGmtModify(new Date());
        billInInfo.setLeadsId(new Long(String.valueOf(params.get("leadsId"))));
        billInInfoService.save(billInInfo);

        BillInEntity billInEntity = billInDao.selectByBillId(new Long(String.valueOf(params.get("billId"))));
        List<BillInInfoEntity> list= billInInfoService.selectAll(billInInfo.getBillId());

        billInEntity.setNum(list.get(0).getLeadsNum());

        //还有一个没有写，就是根据传过来的客户的id查询客户，找到提成，然后称一下
        billInEntity.setAmount( list.get(0).getLeadsCount());
        billInService.updateById(billInEntity);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:billininfo:update")
    public R update(@RequestBody BillInInfoEntity billInInfo){
        ValidatorUtils.validateEntity(billInInfo);
        billInInfoService.updateById(billInInfo);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:billininfo:delete")
    public R delete(@RequestBody Long[] ids){
        billInInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
