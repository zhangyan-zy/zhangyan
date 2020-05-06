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
        BillInEntity billIn = billInService.getById(id);
        BillInInfoEntity billInInfo = billInInfoService.getByBillId(billIn.getId());
        Long leadsId = billInInfo.getLeadsId();
        Integer userId = sysUserDao.selectUserNameByleadsId(leadsId);
        billInInfo.setUserId(userId);
        return R.ok().put("billInInfo", billInInfo);
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:billininfo:save")
    public R save(@RequestBody Object[] objects){
        //生成客户账单
        BillInEntity billIn = new BillInEntity();
        billIn.setGmtCreate(new Date());
        billIn.setCustomerId(new Long(0));
        billIn.setGmtModify(new Date());
        billIn.setBillDate(new Date());
        billIn.setNum(0);
        billIn.setStatus(1);
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

          BigDecimal newAoumt=new BigDecimal(0);
          Integer countNum=0;
           for(int i=0;i<objects.length;i++){
               Map leadsEntity= (Map) objects[i];
               long leadsId = Long.valueOf(String.valueOf(leadsEntity.get("id"))).longValue();
               BigDecimal leadsAmount=new BigDecimal(leadsEntity.get("amount").toString());
               long customerId = Long.valueOf(String.valueOf(leadsEntity.get("parentId"))).longValue();
               List<BillInInfoEntity> billInfoList=  billInInfoService.selectByLeadsId(leadsId);
               if(billInfoList.size()<=0) {
                   //找到客户的提成
                   SysUserEntity user = sysUserDao.selectCustomer(customerId);
                   BigDecimal commission = user.getCommission();

                   //生成leads账单
                   BillInInfoEntity billInInfo = new BillInInfoEntity();
                   billInInfo.setAmount(leadsAmount.multiply(commission));
                   billInInfo.setBillId(billIn.getId());
                   billInInfo.setGmtCreate(new Date());
                   billInInfo.setGmtModify(new Date());
                   billInInfo.setLeadsId(leadsId);
                   billInInfoService.save(billInInfo);
                   countNum=++countNum;
                   //根据账单id找到客户账单
                   BillInEntity billInEntity = billInDao.selectByBillId(billInInfo.getBillId());

                   //算出客户账单的Amount
                   newAoumt= newAoumt.add(leadsAmount.multiply(commission));
                   billInEntity.setAmount(newAoumt);
                   billInEntity.setNum(countNum);
                   billInEntity.setCustomerId(customerId);
                   billInService.updateById(billInEntity);
               }
           }
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
            List<BillInInfoEntity> list = billInInfoService.selectAll(billInInfo1.getBillId());
//          billInEntity.setNum(list.get(0).getLeadsNum());
            SysUserEntity user = sysUserDao.selectCustomer(billInEntity.getCustomerId());
            BigDecimal commission = user.getCommission();
            //算出客户账单的Amount
            BigDecimal newAoumt = list.get(0).getLeadsCount().multiply(commission);
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

    /**
     * 根据账单Id查询列表
     */
    @RequestMapping("/selectByBillId")
    public R selectByBillId(@RequestParam Map<String, Object> params){

        PageUtils page = billInInfoService.selectByBillId(params);
        return R.ok().put("page", page);
    }

}
