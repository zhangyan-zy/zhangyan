package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-02-11 21:41:52
 */
@Data
@TableName("tb_goods")
public class GoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 重量
	 */
	private Double weight;
	/**
	 * 描述
	 */
	private String description;

	private int typeId;

	@TableField(exist = false)
	private String typeName;
}
