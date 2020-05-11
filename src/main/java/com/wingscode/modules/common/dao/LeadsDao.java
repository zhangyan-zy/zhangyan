package com.wingscode.modules.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingscode.modules.common.entity.LeadsEntity;
import com.wingscode.modules.common.entity.TraceEntity;
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

//    IPage<AdminCustomerVO> selectCoustomerList(@Param("parentId") Integer parentId, @Param("date1") String date1, @Param("date2") String date2, Page page);

    void addiphone(@Param("leadsId") Long leadsId);

    /**
     * 查询所有省份
     * @return
     */
    List<String> selectProvince();

    List<String> selectCity(@Param("province") String province);

    void addTracs(@Param("traceEntity") TraceEntity traceEntity);

    IPage<TraceEntity> selectTrace(Page page ,@Param("leadsId") Integer leadsId);

    IPage<TraceEntity> selectTraceByCustomer(@Param("page")Page page ,@Param("username") String username,@Param("mobile") String mobile,
                              @Param("status") String status,@Param("amount1") Integer amount1,
                              @Param("amount2") Integer amount2,@Param("date1") String date1,
                              @Param("date2") String date2);

    List<LeadsEntity> selectCustomerLeads(@Param("parentId") Long parentId);

    List<LeadsEntity> selectLeadsByCustomerId(@Param("customerId") Long customerId);

    IPage<LeadsEntity> selectleadsrList(@Param("page")Page page,@Param("customerId") Long customerId);

     void updateLeadsStatusByLeadsId(@Param("leadsId") Long leadsId);
}
