package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.entity.GoodsEntity;
import com.wingscode.modules.common.entity.OrdersEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单表
 * 
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-14 17:36:42
 */
@Mapper
public interface OrdersDao extends BaseMapper<OrdersEntity> {

    Page<GoodsEntity> selectAll(@Param("page")Page page,
                                @Param("orderId") String orderId,
                                @Param("goodsName") String goodsId,
                                @Param("orderStatus") String orderStatus,
                                @Param("storeId") String storeId);

}
