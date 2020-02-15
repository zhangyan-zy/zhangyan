package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.entity.LeadsEntity;

import java.util.List;
import java.util.Map;

/**
 * @author CrazyHuskar
 * @email jiangshengkang@wingscode.com
 * @date 2019-11-18 12:20:12
 */
public interface LeadsService extends IService<LeadsEntity> {
    List<Long> queryList(Long parentId, String name);

    PageUtils listByStaff(Map<String, Object> params, Long parentId, Long staffId);

    PageUtils listByWorker(Map<String, Object> params, Long parentId, Long workerId);

    PageUtils listByCustomer(Map<String, Object> params, Long parentId);

    PageUtils waitList(Map<String, Object> params, Long workerId);

}

