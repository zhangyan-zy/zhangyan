package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.dao.BillInDao;
import com.wingscode.modules.common.entity.BillInEntity;
import com.wingscode.modules.common.entity.StationEntity;
import com.wingscode.modules.common.service.BillInService;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;



@Service("billInService")
public class BillInServiceImpl extends ServiceImpl<BillInDao, BillInEntity> implements BillInService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // IPage<BillInEntity> page = this.page(
        //         new Query<BillInEntity>().getPage(params),
        //         new QueryWrapper<BillInEntity>()
        // );
        int page = 1;
        if(!StringUtil.isNullOrEmpty((String) params.get("page"))){
            page = Integer.parseInt((String) params.get("page"));
        }
        int limit = 10;
        if(!StringUtil.isNullOrEmpty((String) params.get("limit"))){
            limit = Integer.parseInt((String) params.get("limit"));
        }


        int status = -1;
        if(!StringUtil.isNullOrEmpty((String) params.get("status"))){
            status = Integer.parseInt((String) params.get("status"));
        }


        int customerUserId = 0;
        if(!StringUtil.isNullOrEmpty((String) params.get("customerUserId"))){
            customerUserId = Integer.parseInt((String) params.get("customerUserId"));
        }


        Date billDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        if(!StringUtil.isNullOrEmpty((String) params.get("billDate"))){
            try {
              billDate=  formatter.parse((String) params.get("billDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        Page<StationEntity> pageArt = new Page<StationEntity>(page, limit);
        return new PageUtils(baseMapper.selectAllList(pageArt,status,customerUserId,billDate));

    }

    @Override
    public List<BillInEntity> queryBysn(String sn) {
        return baseMapper.queryBysn(sn);
    }

}
