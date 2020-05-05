package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.Query;
import com.wingscode.modules.common.dao.StationDao;
import com.wingscode.modules.common.entity.StationEntity;
import com.wingscode.modules.common.service.StationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service("stationService")
public class StationServiceImpl extends ServiceImpl<StationDao, StationEntity> implements StationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StationEntity> page = this.page(
                new Query<StationEntity>().getPage(params),
                new QueryWrapper<StationEntity>()
                .like(StringUtils.isNotBlank((String) params.get("key")),"name", params.get("key"))
        );

        return new PageUtils(page);
    }

    @Override
    public List<StationEntity> queryAll() {
         List<StationEntity> list= baseMapper.selectAll();
         return  list;
    }


}
