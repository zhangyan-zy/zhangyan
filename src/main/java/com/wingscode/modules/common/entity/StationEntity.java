package com.wingscode.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 站点表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
@Data
@TableName("tb_station")
public class StationEntity implements Serializable {
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
	 * 站点名称
	 */
	private String name;

}
