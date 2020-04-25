package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 账单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
@Data
@TableName("tb_bill_out")
public class BillOutEntity implements Serializable {
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
	 * 站点
	 */
	private Long station;
	/**
	 * 人数
	 */
	private Integer num;
	/**
	 * 人力成本
	 */
	private BigDecimal cost1;
	/**
	 * 话费
	 */
	private BigDecimal cost2;
	/**
	 * 运营成本
	 */
	private BigDecimal cost3;
	/**
	 * 总成本
	 */
	private BigDecimal allCost;

	/**
	 * 站点名
	 */
	@TableField(exist = false)
	private String stationName;

}
