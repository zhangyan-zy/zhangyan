package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.LeadsLogEntity;

import java.util.Map;

/**
 * @author CrazyHuskar
 * @email jiangshengkang@wingscode.com
 * @date 2019-11-18 12:20:12
 */
public interface LeadsLogService extends IService<LeadsLogEntity> {

    PageUtils queryPage(Map<String, Object> params, Long parentId);
}

