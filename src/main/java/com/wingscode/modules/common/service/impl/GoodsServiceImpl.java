package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.dao.GoodsDao;
import com.wingscode.modules.common.entity.GoodsEntity;
import com.wingscode.modules.common.service.GoodsService;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, GoodsEntity> implements GoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String name = (String) params.get("name");
        String typeId = (String) params.get("typeId");

        System.out.println(typeId);
        System.out.println(name);
        int page = 1;
        if(!StringUtil.isNullOrEmpty((String) params.get("page"))){
            page = Integer.parseInt((String) params.get("page"));
        }
        int limit = 10;
        if(!StringUtil.isNullOrEmpty((String) params.get("limit"))){
            limit = Integer.parseInt((String) params.get("limit"));
        }

        Page<GoodsEntity> pageArt = new Page<>(page, limit);

        return new PageUtils(baseMapper.selectAll(pageArt,name,typeId));
    }

}