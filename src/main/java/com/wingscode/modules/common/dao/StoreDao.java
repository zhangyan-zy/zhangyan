package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.entity.StoreEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-12 19:10:29
 */
@Mapper
public interface StoreDao extends BaseMapper<StoreEntity> {

    Page<StoreEntity> selectAll(@Param("page")Page page, @Param("name") String name);

}
