package com.wingscode.modules.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.vo.AdminAgentAddEntityVo;

import java.util.Map;

/**
 * Author：张延
 * Date：2020-03-11 11:01
 * Description：<描述>
 */
public interface StatisticalService {
     /*
     首页查询
      */
     PageUtils selectCustomerList(Map<String, Object> params);

    PageUtils allWorker(Map<String, Object> params);
    /*
    坐席端查询
     */
    IPage<AdminAgentAddEntityVo> selectAgentList(Map<String, Object> params);


}
