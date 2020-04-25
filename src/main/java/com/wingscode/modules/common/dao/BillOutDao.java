package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.entity.BillOutEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 账单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
@Mapper
public interface BillOutDao extends BaseMapper<BillOutEntity> {

   Page<BillOutEntity> selectAllList(@Param("page")Page page,@Param("station")Integer station);
}
