package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingscode.modules.common.entity.CouponEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券表
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-14 17:36:42
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
