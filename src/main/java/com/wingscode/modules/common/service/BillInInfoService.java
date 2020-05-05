package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.BillInInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 账单详单
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
public interface BillInInfoService extends IService<BillInInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<BillInInfoEntity> selectAll(Long billId);

    BillInInfoEntity getByBillId(Long billId);

    List<BillInInfoEntity>  selectByLeadsId(Long leadsId);

    void deleteByBillId(Long billId);

    PageUtils selectByBillId(Map<String, Object> params);
}

