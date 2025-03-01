package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.entity.CouponEntity;
import com.wingscode.modules.common.entity.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 优惠券表
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-14 17:36:42
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {

    Page<GoodsEntity> selectAll(@Param("page")Page page, @Param("name") String name);

}
