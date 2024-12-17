package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.StoreEntity;

import java.util.Map;

/**
 * 
 *
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-12 19:10:29
 */
public interface StoreService extends IService<StoreEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

