package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.entity.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-02-11 21:11:16
 */
@Mapper
public interface GoodsDao extends BaseMapper<GoodsEntity> {


    Page<GoodsEntity> selectAll(@Param("page")Page page,@Param("name") String name, @Param("typeId") String typeId);

}
