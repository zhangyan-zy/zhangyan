package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品类别
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-02-11 21:48:22
 */
@Data
@TableName("tb_type")
public class TypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String name;

}
