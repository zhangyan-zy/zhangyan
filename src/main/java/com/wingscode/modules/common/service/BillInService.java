package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.BillInEntity;

import java.util.List;
import java.util.Map;

/**
 * 账单表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
public interface BillInService extends IService<BillInEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<BillInEntity> queryBysn(String sn);
}

