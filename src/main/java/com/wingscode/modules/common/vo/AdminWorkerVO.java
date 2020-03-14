package com.wingscode.modules.common.vo;

import lombok.Data;

/**
 * Author：张延
 * Date：2020-03-13 13:09
 * Description：<描述>
 */
@Data
public class AdminWorkerVO {

    /**
     * 客户名
     */
    private String username;
    /**
     * 坐席名
     */
    private String leadsname;
    /**
     * 总leads
     */
    private Integer sumLeads;
    /**
     * 每日新增
     */
    private Integer addDaily;
    /**
     * 每日平均
     */
    private Integer addVag;


}
