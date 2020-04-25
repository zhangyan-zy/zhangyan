package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 账单详单
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
@Data
@TableName("tb_bill_in_info")
public class BillInInfoEntity implements Serializable {
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
	 * 账单id
	 */
	private Long billId;
	/**
	 * leadsid
	 */
	private Long leadsId;
	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 *  coustomer下的leads总金额
	 */
	@TableField(exist = false)
	private BigDecimal leadsCount;

	/**
	 *  coustomer下的leads总数量
	 */
	@TableField(exist = false)
	private Integer leadsNum;

}
