package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.app.entity.UserEntity;
import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.vo.CoustomerCountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Author：张延
 * Date：2020-03-09 16:31
 * Description：<描述>
 */
@Mapper
public interface StatisticalDao extends BaseMapper<UserEntity> {

    Page<CoustomerCountEntity> selectCoustomerList(@Param("parentId") Integer parentId, @Param("date1") String date1, @Param("date2") String date2, Page page);

    Page<LeadsEntity> selectAgentList(@Param("parentId") Long parentId, @Param("date1") String date1, @Param("date2") String date2, Page page);
    // List  queryZxLeadersList(Map<String, Object> params);
    //
    // List  addAndShowLeadsList(Map<String, Object> params);
    //
    // List queryCostumerList(Map<String, Object> params);
    //
    // List queryAgentsList(Map<String, Object> params);

}
