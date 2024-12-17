package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单表
 * 
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-14 17:36:42
 */
@Data
@TableName("tb_orders")
public class OrdersEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 商品编号
	 */
	private Integer goodId;
	/**
	 * 店铺编号
	 */
	private Integer storeId;
	/**
	 * 订单金额
	 */
	private Long score;
	/**
	 * 订单状态（0制作中 1成功 2失败）
	 */
	private Integer state;
	/**
	 * 订单创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

	@TableField(exist = false)
	private String goodName;
	@TableField(exist = false)
	private String storeName;
}
