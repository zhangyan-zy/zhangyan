package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券表
 * 
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-14 17:36:42
 */
@Data
@TableName("tb_coupon")
public class CouponEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 优惠券名称
	 */
	private String name;
	/**
	 * 店铺id
	 */
	private Integer storeId;
	/**
	 * 有效期
	 */
	private Date expiryDate;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 优惠券金额
	 */
	private BigDecimal price;

}
