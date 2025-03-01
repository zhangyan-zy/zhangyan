package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.StoreLogEntity;

import java.util.Map;

/**
 * 店铺公告
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-02-20 21:33:50
 */
public interface StoreLogService extends IService<StoreLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

