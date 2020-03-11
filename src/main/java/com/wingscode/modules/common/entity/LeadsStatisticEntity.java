package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * Author：张延
 * Date：2020-03-11 9:15
 * Description：<描述>
 */
public class LeadsStatisticEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreat;

    /**
     * 修改时间
     */
    private Date gmtModifies;

    /**
     * 统计记录时间
     */
    private Date statisticsTime;

    /**
     * 客户ID
     */
    private Long parentId;

    /**
     * 新增总数
     */
    private Long newCount;

    /**
     * 所有总数
     */
    private Long allCount;

}
