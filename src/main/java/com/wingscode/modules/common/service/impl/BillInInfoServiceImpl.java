package com.wingscode.modules.common.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.Query;
import com.wingscode.modules.common.dao.BillInInfoDao;
import com.wingscode.modules.common.entity.BillInInfoEntity;
import com.wingscode.modules.common.service.BillInInfoService;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("billInInfoService")
public class BillInInfoServiceImpl extends ServiceImpl<BillInInfoDao, BillInInfoEntity> implements BillInInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BillInInfoEntity> page = this.page(
                new Query<BillInInfoEntity>().getPage(params),
                new QueryWrapper<BillInInfoEntity>()
                .eq("bill_id",StringUtil.isNullOrEmpty((String) params.get("billId")))
        );
        return new PageUtils(page);
    }

    @Override
    public List<BillInInfoEntity> selectAll(Long billId) {
        return baseMapper.selectAll(billId);
    }

    @Override
    public BillInInfoEntity getByBillId(Long billId) {
        return baseMapper.selectByBillId(billId);
    }

    @Override
    public List<BillInInfoEntity> selectByLeadsId(Long leadsId) {
        return baseMapper.selectByLeadsId(leadsId);
    }

    @Override
    public void deleteByBillId(Long billId) {
        baseMapper.deleteByBillId(billId);
    }

    @Override
    public PageUtils selectByBillId(Map<String, Object> params) {
        // IPage<BillInInfoEntity> page = this.page(
        //         new Query<BillInInfoEntity>().getPage(params),
        //         new QueryWrapper<BillInInfoEntity>()
        //         .eq("bill_id",new Long((String) params.get("billId")))
        // );
        int page = 1;
        if(!StringUtil.isNullOrEmpty((String) params.get("page"))){
            page = Integer.parseInt((String) params.get("page"));
        }
        int limit = 10;
        if(!StringUtil.isNullOrEmpty((String) params.get("limit"))){
            limit = Integer.parseInt((String) params.get("limit"));
        }

        int billId = 0;
        if(!StringUtil.isNullOrEmpty((String) params.get("billId"))){
            billId = Integer.parseInt((String) params.get("billId"));
        }
        Page<BillInInfoEntity> pageArt = new Page<>(page, limit);

        return new PageUtils(baseMapper.selectbillInfoByBillId(pageArt,billId));
    }

}
