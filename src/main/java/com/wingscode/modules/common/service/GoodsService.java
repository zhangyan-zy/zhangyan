package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.GoodsEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-02-11 21:11:16
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

