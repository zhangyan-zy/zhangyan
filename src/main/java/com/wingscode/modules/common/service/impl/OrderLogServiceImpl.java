package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.Query;
import com.wingscode.modules.common.dao.OrderLogDao;
import com.wingscode.modules.common.entity.OrderLogEntity;
import com.wingscode.modules.common.service.OrderLogService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("orderLogService")
public class OrderLogServiceImpl extends ServiceImpl<OrderLogDao, OrderLogEntity> implements OrderLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderLogEntity> page = this.page(
                new Query<OrderLogEntity>().getPage(params),
                new QueryWrapper<OrderLogEntity>()
        );

        return new PageUtils(page);
    }

}