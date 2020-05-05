package com.wingscode.modules.common.controller;

import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.common.validator.ValidatorUtils;
import com.wingscode.modules.common.dao.LeadsDao;
import com.wingscode.modules.common.entity.BillInEntity;
import com.wingscode.modules.common.service.BillInInfoService;
import com.wingscode.modules.common.service.BillInService;
import com.wingscode.modules.sys.dao.SysUserDao;
import com.wingscode.util.MyUtilUUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


/**
 * 账单表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
@RestController
@RequestMapping("common/billin")
@Api("账本")
public class BillInController {
    @Autowired
    private BillInService billInService;
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private LeadsDao leadsDao;
    @Autowired
    private BillInInfoService billInInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:billin:list")
    @ApiOperation("列表")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = billInService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:billin:info")
    @ApiOperation("信息")
    public R info(@PathVariable("id") Long id){
        BillInEntity billIn = billInService.getById(id);

        return R.ok().put("billIn", billIn);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:billin:save")
    @ApiOperation("保存")
    public R save(@RequestBody BillInEntity billIn){
        billIn.setGmtCreate(new Date());
        billIn.setGmtModify(new Date());
        billIn.setBillDate(new Date());
        billIn.setNum(0);
        billIn.setStatus(0);
        //产品编号随机生成，如果重复，重新添加
        Boolean flag = true;
        while (flag) {
            String sn = MyUtilUUID.getCode();
            Map params = new HashMap();
            params.put("sn", sn);
            List<BillInEntity> list = billInService.queryBysn(sn);
            if (list.size() == 0) {
                flag = false;
                billIn.setSn(sn);
                break;
            }
        }
        billIn.setAmount(new BigDecimal(0));
        billInService.save(billIn);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:billin:update")
    @ApiOperation("修改")
    public R update(@RequestBody BillInEntity billIn){
        billIn.setGmtModify(new Date());
        ValidatorUtils.validateEntity(billIn);
        billInService.updateById(billIn);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:billin:delete")
    @ApiOperation("删除")
    public R delete(@RequestBody Long[] ids){
        for(int i=0;i<ids.length;i++){
            BillInEntity billInEntity=billInService.getById(ids[i]);
            billInInfoService.deleteByBillId(billInEntity.getId());
        }
        billInService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
