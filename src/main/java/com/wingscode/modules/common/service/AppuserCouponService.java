package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.AppuserCouponEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-12-26 22:22:29
 */
public interface AppuserCouponService extends IService<AppuserCouponEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

