package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.entity.BillInEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 账单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
@Mapper
public interface BillInDao extends BaseMapper<BillInEntity> {

   Page<BillInEntity> selectAllList(@Param("page")Page page,@Param("status")Integer status,
                                    @Param("customerUserId")Integer customerUserId,@Param("billDate")Date billDate);

   List<BillInEntity> queryBysn(@Param("sn") String sn);

   BillInEntity selectByBillId(@Param("billId") Long billId);
}
