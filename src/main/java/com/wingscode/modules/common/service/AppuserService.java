package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.AppuserEntity;

import java.util.Map;

/**
 * 用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-12-26 22:57:56
 */
public interface AppuserService extends IService<AppuserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

