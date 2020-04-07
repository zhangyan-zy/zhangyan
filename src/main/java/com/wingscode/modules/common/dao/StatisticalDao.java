package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.vo.AdminAgentAddVo;
import com.wingscode.modules.common.vo.AdminCustomerVO;
import com.wingscode.modules.common.vo.AdminStaffVo;
import com.wingscode.modules.common.vo.AdminWorkerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 坐席端统计不带分页
     */
     List<AdminWorkerVO> allWorkerExcel(@Param("parentId") Long parentId, @Param("date1") String date1, @Param("date2") String date2);
    /**
     * 坐席端每日查询
     */
    IPage<AdminAgentAddVo> allWorker1(Page page, @Param("parentId") Long parentId, @Param("date") String date);

    /**
     * 统计员工
     */
    IPage<AdminStaffVo>  allStaff(Page page, @Param("userId") Long userId, @Param("date1") String date1, @Param("date2") String date2, @Param("like") String like );


}
