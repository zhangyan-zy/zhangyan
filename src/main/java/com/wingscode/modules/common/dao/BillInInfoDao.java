package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingscode.modules.common.entity.BillInInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 账单详单
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
@Mapper
public interface BillInInfoDao extends BaseMapper<BillInInfoEntity> {
	List<BillInInfoEntity> selectAll(@Param("billId") Long billId);
}
