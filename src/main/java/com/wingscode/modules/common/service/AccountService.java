package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * @author CrazyHuskar
 * @email jiangshengkang@wingscode.com
 * @date 2019-11-18 12:20:12
 */
public interface AccountService extends IService<SysUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SysUserEntity> allList(Long roleId, Long parentId);

    void setWorkerAdmin(Long userId);
}

