package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.dao.BillOutDao;
import com.wingscode.modules.common.entity.BillOutEntity;
import com.wingscode.modules.common.entity.StationEntity;
import com.wingscode.modules.common.service.BillOutService;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("billOutService")
public class BillOutServiceImpl extends ServiceImpl<BillOutDao, BillOutEntity> implements BillOutService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // IPage<BillOutEntity> page = this.page(
        //         new Query<BillOutEntity>().getPage(params),
        //         new QueryWrapper<BillOutEntity>()
        // );
        int page = 1;
        if(!StringUtil.isNullOrEmpty((String) params.get("page"))){
            page = Integer.parseInt((String) params.get("page"));
        }
        int limit = 10;
        if(!StringUtil.isNullOrEmpty((String) params.get("limit"))){
            limit = Integer.parseInt((String) params.get("limit"));
        }
        int station = -1;
        if(!StringUtil.isNullOrEmpty((String) params.get("station"))){
            station = Integer.parseInt((String) params.get("station"));
        }
        Page<StationEntity> pageArt = new Page<StationEntity>(page, limit);

        return new PageUtils(baseMapper.selectAllList(pageArt,station));
    }

}
