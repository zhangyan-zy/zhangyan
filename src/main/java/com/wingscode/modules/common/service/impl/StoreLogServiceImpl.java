package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.dao.StoreLogDao;
import com.wingscode.modules.common.entity.StoreLogEntity;
import com.wingscode.modules.common.service.StoreLogService;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("storeLogService")
public class StoreLogServiceImpl extends ServiceImpl<StoreLogDao, StoreLogEntity> implements StoreLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("key");

        int page = 1;
        if(!StringUtil.isNullOrEmpty((String) params.get("page"))){
            page = Integer.parseInt((String) params.get("page"));
        }
        int limit = 10;
        if(!StringUtil.isNullOrEmpty((String) params.get("limit"))){
            limit = Integer.parseInt((String) params.get("limit"));
        }

        Page<StoreLogEntity> pageArt = new Page<>(page, limit);

        return new PageUtils(baseMapper.selectAll(pageArt,name));
    }

}