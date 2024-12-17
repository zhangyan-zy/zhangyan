package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.OrderLogEntity;

import java.util.Map;

/**
 * 订单日志
 *
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-14 17:36:42
 */
public interface OrderLogService extends IService<OrderLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

