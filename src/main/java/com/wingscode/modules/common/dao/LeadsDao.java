package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.vo.AdminCustomerVO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author CrazyHuskar
 * @email jiangshengkang@wingscode.com
 * @date 2019-11-18 12:20:12
 */
@Mapper
public interface LeadsDao extends BaseMapper<LeadsEntity> {
    List<Long> queryList(Long parentId, String name);

    IPage<AdminCustomerVO> selectCoustomerList(@Param("parentId") Integer parentId, @Param("date1") String date1, @Param("date2") String date2, Page page);

}
