package com.wingscode.modules.common.vo;

import lombok.Data;

/**
 * Author：张延
 * Date：2020-03-13 19:05
 * Description：<描述>
 */
@Data
public class AdminAgentAddVo {

    /**
    增加总数
     */
    private Integer asumLeads;
    /**
    姓名
     */
    private String aname;
    /**
    每日新增
     */
    private Integer aaddleads;
    /**
    平均每日新增
     */
    private Double avgadd;

}
