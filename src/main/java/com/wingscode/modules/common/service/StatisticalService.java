package com.wingscode.modules.common.service;

import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.vo.AgentAddEntity;

import java.util.List;
import java.util.Map;

/**
 * Author：张延
 * Date：2020-03-11 11:01
 * Description：<描述>
 */
public interface StatisticalService {

    PageUtils selectCustomerList(Map<String, Object> params);

    PageUtils allWorker(Map<String, Object> params);

    List<AgentAddEntity> selectAgentList(Map<String, Object> params);


}
