package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.TypeEntity;

import java.util.Map;

/**
 * 商品类别
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-02-11 21:48:22
 */
public interface TypeService extends IService<TypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

