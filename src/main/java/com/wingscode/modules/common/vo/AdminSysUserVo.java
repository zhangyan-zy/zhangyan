package com.wingscode.modules.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wingscode.common.validator.group.AddGroup;
import com.wingscode.common.validator.group.UpdateGroup;
import com.wingscode.modules.common.entity.LeadsEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author：张延
 * Date：2020-05-05 9:31
 * Description：<描述>
 */
@Data
public class AdminSysUserVo {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    private Long userId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = AddGroup.class)
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 角色ID列表
     */
    @TableField(exist = false)
    private List<Long> roleIdList;
    /**
     * 是否组长
     */
    @TableField(exist = false)
    private Integer rolePower = 0;

    /**
     * 创建者ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上级id
     */
    private Long parentId;
    @TableField(exist = false)
    private String parentName;

    @TableField(exist = false)
    private Integer allNum;

    @TableField(exist = false)
    private Integer todayNum;

    /**
     * 提成
     */
    private BigDecimal commission;

    private  List<LeadsEntity>  leadsEntities;
}
