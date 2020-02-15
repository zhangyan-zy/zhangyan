package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author CrazyHuskar
 * @email jiangshengkang@wingscode.com
 * @date 2019-11-18 12:20:12
 */
@Data
@TableName("tb_leads_log")
public class LeadsLogEntity implements Serializable {
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
     * 客户id
     */
    private Long leadsId;
    /**
     * 处理人
     */
    private Long disposeUser;
    /**
     * 操作钱、前状态
     */
    private Integer statusOld;
    /**
     * 操作后状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private String leadsName;

    @TableField(exist = false)
    private String disposeUserName;
}
