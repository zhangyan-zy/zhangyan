package com.wingscode.modules.common.controller;

import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.common.validator.ValidatorUtils;
import com.wingscode.modules.common.dao.BillInDao;
import com.wingscode.modules.common.entity.BillInEntity;
import com.wingscode.modules.common.entity.BillInInfoEntity;
import com.wingscode.modules.common.service.BillInInfoService;
import com.wingscode.modules.common.service.BillInService;
import com.wingscode.modules.sys.dao.SysUserDao;
import com.wingscode.modules.sys.entity.SysUserEntity;
import com.wingscode.util.MyUtilUUID;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


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
    @Resource
    private SysUserDao sysUserDao;
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
    public R save(@RequestBody Map<String, Object> params){
        //生成客户账单
        BillInEntity billIn=new BillInEntity();
        billIn.setGmtCreate(new Date());
        billIn.setCustomerId(new Long(String.valueOf(params.get("customerId"))));
        billIn.setGmtModify(new Date());
        billIn.setBillDate(new Date());
        billIn.setNum(0);
        billIn.setStatus(0);
        //产品编号随机生成，如果重复，重新添加
        Boolean flag = true;
        while (flag) {
            String sn = MyUtilUUID.getCode();
            Map param = new HashMap();
            param.put("sn", sn);
            List<BillInEntity> list = billInService.queryBysn(sn);
            if (list.size() == 0) {
                flag = false;
                billIn.setSn(sn);
                break;
            }
        }
        billIn.setAmount(new BigDecimal(0));
        billInService.save(billIn);

        Long billId = billIn.getId();

        //生成leads账单
        BillInInfoEntity billInInfo=new BillInInfoEntity();
        billInInfo.setAmount(new BigDecimal(String.valueOf(params.get("amount"))));
        billInInfo.setBillId(billId);
        billInInfo.setGmtCreate(new Date());
        billInInfo.setGmtModify(new Date());
        billInInfo.setLeadsId(new Long(String.valueOf(params.get("leadsId"))));
        billInInfoService.save(billInInfo);

        //根据leads账单找到客户账单
        BillInEntity billInEntity = billInDao.selectByBillId(billInInfo.getBillId());
        List<BillInInfoEntity> list= billInInfoService.selectAll(billInInfo.getBillId());
        billInEntity.setNum(list.get(0).getLeadsNum());
        SysUserEntity user = sysUserDao.selectCustomer(billInEntity.getCustomerId());
        BigDecimal commission = user.getCommission();
        //算出客户账单的Amount
        BigDecimal newAoumt=list.get(0).getLeadsCount().multiply(commission);
        billInEntity.setAmount(newAoumt);
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
        BillInInfoEntity billInInfo1 = billInInfoService.getById(billInInfo.getId());
        //根据leads账单找到客户账单
        BillInEntity billInEntity = billInDao.selectByBillId(billInInfo1.getBillId());
        List<BillInInfoEntity> list= billInInfoService.selectAll(billInInfo1.getBillId());
//        billInEntity.setNum(list.get(0).getLeadsNum());
        SysUserEntity user = sysUserDao.selectCustomer(billInEntity.getCustomerId());
        BigDecimal commission = user.getCommission();
        //算出客户账单的Amount
        BigDecimal newAoumt=list.get(0).getLeadsCount().multiply(commission);
        billInEntity.setAmount(newAoumt);
        billInService.updateById(billInEntity);

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
