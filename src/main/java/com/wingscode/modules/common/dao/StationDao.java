package com.wingscode.modules.common.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingscode.modules.common.entity.StationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 站点表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
@Mapper
public interface StationDao extends BaseMapper<StationEntity> {

    /**
     * 查询所有的站点
     */
    List<StationEntity> selectAll();
}
