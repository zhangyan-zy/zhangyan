package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 账单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
@Data
@TableName("tb_bill_in")
public class BillInEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	private Date gmtModify;
	/**
	 * 账单时间
	 */
	private Date billDate;
	/**
	 * 账单编号
	 */
	private String sn;
	/**
	 * 账单内leads数量
	 */
	private Integer num;
	/**
	 * 客户id
	 */
	private Long customerId;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 状态（0：关闭；1：已结算；2：待结算）
	 */
	private Integer status;

   //客户名称
	private String customerUserName;
}
