package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.dao.StatisticalDao;
import com.wingscode.modules.common.service.StatisticalService;
import com.wingscode.modules.common.vo.AdminAgentAddEntityVo;
import com.wingscode.modules.common.vo.AdminCustomerVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Author：张延
 * Date：2020-03-11 11:01
 * Description：<描述>
 */
@Service("StatisticalService")
public class StatisticalSericeImpl extends ServiceImpl<StatisticalDao, AdminCustomerVO> implements StatisticalService {

    @Override
    public PageUtils selectCustomerList(Map<String, Object> params) {
        Integer page = Integer.parseInt((String) params.get("page"));
        Integer limit = Integer.parseInt((String) params.get("limit"));
        Integer parentId = null;
        if (StringUtils.isNotEmpty((String) params.get("parentId"))) {
            parentId = Integer.parseInt((String) params.get("parentId"));
        }
        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");

        Page<AdminCustomerVO> pages = new Page<>(page, limit);
        return new PageUtils(baseMapper.selectCustomerList(pages,parentId, date1, date2));
    }

    @Override
    public PageUtils allWorker(Map<String, Object> params) {
        return new PageUtils(baseMapper.selectCustomerList(pages,parentId, date1, date2));
    }

    @Override
    public IPage<AdminAgentAddEntityVo> selectAgentList(Map<String, Object> params) {


        System.out.println(params + "090909");
        Integer page = Integer.parseInt((String) params.get("page"));
        Integer limit = Integer.parseInt((String) params.get("limit"));

        System.out.println("121212" + page);
        System.out.println("121212" + limit);
        Integer parentId = null;
        if (StringUtils.isNotEmpty((String) params.get("parentId"))) {
            parentId = Integer.parseInt((String) params.get("parentId"));
        }
        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");

        Page<AdminAgentAddEntityVo> pages = new Page<>(page, limit);
        IPage pagess = baseMapper.selectAgentList( pages,parentId, date1, date2);
        return pagess;
    }

}



