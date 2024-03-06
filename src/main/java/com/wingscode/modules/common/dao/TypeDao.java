package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingscode.modules.common.entity.TypeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品类别
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-02-11 21:48:22
 */
@Mapper
public interface TypeDao extends BaseMapper<TypeEntity> {
	
}
