package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author CrazyHuskar
 * @email jiangshengkang@wingscode.com
 * @date 2019-11-18 12:20:12
 */
@Data
@TableName("tb_leads")
public class LeadsEntity implements Serializable {
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
     * 客户名称
     */
    private String name;
    private String name1;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 微信
     */
    private String webchat;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 是否意向
     */
    private Integer need;
    /**
     * 状态
     * 0：已关闭
     * 1：待分配
     * 2：待处理
     * 3：已响应
     * 4：已成单
     * 5：未成单
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 所属客户
     */
    private Long parentId;
    @TableField(exist = false)
    private String parentName;
    /**
     * 处理人
     */
    private Long disposeUser;
    /**
     * 创建人
     */
    private Long workerId;
    @TableField(exist = false)
    private String statusRemark;
    @TableField(exist = false)
    private String disposeUserName;
    @TableField(exist = false)
    private String workerName;


    private String areaCode;
    private String province;
    private String city;
    private Integer status1;
}
