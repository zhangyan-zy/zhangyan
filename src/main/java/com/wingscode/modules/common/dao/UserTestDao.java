package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingscode.modules.sys.entity.SysUserEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author：张延
 * Date：2020-03-09 16:31
 * Description：<描述>
 */
@Mapper
public interface UserTestDao extends BaseMapper<SysUserEntity> {

    List queryUserList(@Param("parentId") Integer parentId, @Param("date1") String date1, @Param("date2") String date2);

    List OrderAgentList(Map<String, Object> params);


    // List  queryZxLeadersList(Map<String, Object> params);
    //
    // List  addAndShowLeadsList(Map<String, Object> params);
    //
    // List queryCostumerList(Map<String, Object> params);
    //
    // List queryAgentsList(Map<String, Object> params);

}
