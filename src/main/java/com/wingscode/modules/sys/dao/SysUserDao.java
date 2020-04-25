package com.wingscode.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingscode.modules.sys.entity.SysUserEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 根据用户名，查询系统用户
     */
    SysUserEntity queryByUserName(String username);

    List<Long> queryUserIdList(Long parentId);

    /**
     * 查询所有的客户
     */
    List<SysUserEntity> selectCustomerList();

    /**
     * 根据客户Id查询客户
     * @param customerId
     * @return
     */
    SysUserEntity selectCustomer(@Param("customerId") Long customerId);
}
