package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.vo.CoustomerCountEntity;

import java.util.Map;

/**
 * Author：张延
 * Date：2020-03-11 11:01
 * Description：<描述>
 */
public interface StatisticalService {

    Page<CoustomerCountEntity> selectCoustomerList(Map<String, Object> params);

    Page<LeadsEntity> orderAgentList(Map<String, Object> params,Long parentId);

    // List  zxLeadersList(Map<String, Object> params);
    // List addAndShowLeadsList(Map<String, Object> params);
}
