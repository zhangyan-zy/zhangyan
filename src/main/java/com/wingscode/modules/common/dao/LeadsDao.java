package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingscode.modules.common.entity.LeadsEntity;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author CrazyHuskar
 * @email jiangshengkang@wingscode.com
 * @date 2019-11-18 12:20:12
 */
@Mapper
public interface LeadsDao extends BaseMapper<LeadsEntity> {
    List<Long> queryList(Long parentId, String name);



    List  queryLeadersList(Long parentId, Long roleId);
}
