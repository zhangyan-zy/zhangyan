package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-12 19:10:29
 */
@Data
@TableName("tb_store")
public class StoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String no;
	/**
	 * 
	 */
	private String area;
	/**
	 * 
	 */
	private String address;
	/**
	 * 坐标
	 */
	private String coordinate;
	/**
	 * 
	 */
	private Date createtime;

}
