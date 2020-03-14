package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.vo.AdminCustomerVO;
import com.wingscode.modules.common.vo.AdminWorkerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Author：张延
 * Date：2020-03-09 16:31
 * Description：<描述>
 */
@Mapper
public interface StatisticalDao extends BaseMapper<AdminCustomerVO> {
    /**
     * 首页查询
     */
    IPage<AdminCustomerVO> selectCustomerList(Page page, @Param("parentId") Integer parentId, @Param("date1") String date1, @Param("date2") String date2);

    /**
     * 坐席端查询
     */
    IPage<AdminWorkerVO> allWorker(Page page, @Param("parentId") Long parentId, @Param("date1") String date1, @Param("date2") String date2);

    // /**
    //  * 坐席端查询
    //  */
    // IPage<AdminAgentAddVo> selectAgentList(Page page, @Param("parentId") Integer parentId, @Param("date1") String date1, @Param("date2") String date2);

}
