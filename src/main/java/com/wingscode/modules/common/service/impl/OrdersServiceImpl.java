package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.dao.OrdersDao;
import com.wingscode.modules.common.entity.OrdersEntity;
import com.wingscode.modules.common.service.OrdersService;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("ordersService")
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, OrdersEntity> implements OrdersService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String orderId = (String) params.get("orderId");
        String goodsName = (String) params.get("goodsName");
        String orderStatus = (String) params.get("orderStatus");
        String storeId = (String) params.get("storeId");

        int page = 1;
        if(!StringUtil.isNullOrEmpty((String) params.get("page"))){
            page = Integer.parseInt((String) params.get("page"));
        }
        int limit = 10;
        if(!StringUtil.isNullOrEmpty((String) params.get("limit"))){
            limit = Integer.parseInt((String) params.get("limit"));
        }

        Page<OrdersEntity> pageArt = new Page<>(page, limit);

        return new PageUtils(baseMapper.selectAll(pageArt,orderId,goodsName,orderStatus,storeId));
    }

}