package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.entity.StoreLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 店铺公告
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-02-20 21:33:50
 */
@Mapper
public interface StoreLogDao extends BaseMapper<StoreLogEntity> {

    Page<StoreLogEntity> selectAll(@Param("page")Page page, @Param("name") String name);
	
}
