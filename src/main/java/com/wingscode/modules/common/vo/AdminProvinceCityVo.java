package com.wingscode.modules.common.vo;

import lombok.Data;

import java.util.List;

/**
 * Author：张延
 * Date：2020-03-26 9:55
 * Description：<描述>
 */
@Data
public class AdminProvinceCityVo {

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */

    private List<String> city;
}
