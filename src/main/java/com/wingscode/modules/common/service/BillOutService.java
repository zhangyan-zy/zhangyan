package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.BillOutEntity;

import java.util.Map;

/**
 * 账单表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
public interface BillOutService extends IService<BillOutEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

