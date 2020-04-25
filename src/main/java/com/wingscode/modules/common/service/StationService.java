package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.StationEntity;

import java.util.List;
import java.util.Map;

/**
 * 站点表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
public interface StationService extends IService<StationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<StationEntity> queryAll();
}

