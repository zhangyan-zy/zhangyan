package com.wingscode.modules.common.service;

import com.wingscode.common.utils.PageUtils;

import java.util.Map;

/**
 * Author：张延
 * Date：2020-03-11 11:01
 * Description：<描述>
 */
public interface StatisticalService {
     /**
      * 首页查询
      */
     PageUtils selectCustomerList(Map<String, Object> params);

    /**
     * 查询所有坐席端列表
     */
     PageUtils allWorker(Map<String, Object> params);
    // /**
    //  *坐席端查询
    //  */
    //  PageUtils selectAgentList(Map<String, Object> params);


}
