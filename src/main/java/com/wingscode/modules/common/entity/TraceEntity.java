package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * Author：张延
 * Date：2020-04-16 14:39
 * Description：<描述>
 */
@Data
public class TraceEntity {

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
    private Date gmtModified;

    /**
     * leadsId
     */
    private Integer leadsId;

    /**
     * 跟进内容
     */
    private String  content;
}
