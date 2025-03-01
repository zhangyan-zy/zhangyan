package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 店铺公告
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2025-02-20 21:33:50
 */
@Data
@TableName("tb_store_log")
public class StoreLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 店铺id
	 */
	private Integer storeId;
	/**
	 * 公告信息
	 */
	private String msg;
	/**
	 * 新品推荐
	 */
	private String newGood;
	/**
	 * 活动信息
	 */
	private String activityMsg;

	@TableField(exist = false)
	private String storeName;

}
