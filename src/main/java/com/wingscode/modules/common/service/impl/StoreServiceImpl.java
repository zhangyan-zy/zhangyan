package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.dao.StoreDao;
import com.wingscode.modules.common.entity.StoreEntity;
import com.wingscode.modules.common.service.StoreService;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("storeService")
public class StoreServiceImpl extends ServiceImpl<StoreDao, StoreEntity> implements StoreService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String name = (String) params.get("name");

        int page = 1;
        if(!StringUtil.isNullOrEmpty((String) params.get("page"))){
            page = Integer.parseInt((String) params.get("page"));
        }
        int limit = 10;
        if(!StringUtil.isNullOrEmpty((String) params.get("limit"))){
            limit = Integer.parseInt((String) params.get("limit"));
        }

        Page<StoreEntity> pageArt = new Page<>(page, limit);

        return new PageUtils(baseMapper.selectAll(pageArt,name));
    }

}