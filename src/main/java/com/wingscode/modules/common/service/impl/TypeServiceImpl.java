package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.Query;
import com.wingscode.modules.common.dao.TypeDao;
import com.wingscode.modules.common.entity.TypeEntity;
import com.wingscode.modules.common.service.TypeService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("typeService")
public class TypeServiceImpl extends ServiceImpl<TypeDao, TypeEntity> implements TypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TypeEntity> page = this.page(
                new Query<TypeEntity>().getPage(params),
                new QueryWrapper<TypeEntity>()
        );

        return new PageUtils(page);
    }

}