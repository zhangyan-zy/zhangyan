package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.OrdersEntity;

import java.util.Map;

/**
 * 订单表
 *
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-14 17:36:42
 */
public interface OrdersService extends IService<OrdersEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

