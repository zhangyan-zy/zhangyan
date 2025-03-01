package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.entity.AppuserCouponEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-12-26 22:22:29
 */
@Mapper
public interface AppuserCouponDao extends BaseMapper<AppuserCouponEntity> {
    Page<AppuserCouponEntity> selectAll(@Param("page")Page page, @Param("name") String name);

}
