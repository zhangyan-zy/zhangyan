package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.entity.AppuserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-12-26 22:57:56
 */
@Mapper
public interface AppuserDao extends BaseMapper<AppuserEntity> {
    Page<AppuserEntity> selectAll(@Param("page")Page page, @Param("name") String name);

}
