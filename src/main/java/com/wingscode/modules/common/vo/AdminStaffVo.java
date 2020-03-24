package com.wingscode.modules.common.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Author：张延
 * Date：2020-03-24 10:59
 * Description：<描述>
 */
@Data
public class AdminStaffVo {

    /**
     * 员工
     */
     private String leadsname;
    /**
     * 分配量
     */

    private BigDecimal quantum;
    /**
     * 加微已响应
     */
    private BigDecimal ResponseRate;
    /**
     * 响应率
     */
    private BigDecimal responseAvg;
    /**
     * 签单金额
     */
    private BigDecimal SumAmount;
    /**
     * 签单率
     */
    private BigDecimal SigningRate;
}
