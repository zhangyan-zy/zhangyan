package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingscode.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Author：张延
 * Date：2020-03-09 16:31
 * Description：<描述>
 */
@Mapper
public interface UserDao extends BaseMapper<SysUserEntity> {

    List  queryUserList(Long parentId, Long roleId);

    List  queryZxLeadersList(Long parentId, Long roleId);
}
