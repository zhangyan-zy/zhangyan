package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingscode.modules.common.entity.OrderLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单日志
 * 
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-14 17:36:42
 */
@Mapper
public interface OrderLogDao extends BaseMapper<OrderLogEntity> {
	
}
