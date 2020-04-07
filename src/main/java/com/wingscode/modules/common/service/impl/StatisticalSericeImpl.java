package com.wingscode.modules.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.modules.common.dao.StatisticalDao;
import com.wingscode.modules.common.service.StatisticalService;
import com.wingscode.modules.common.vo.AdminCustomerVO;
import com.wingscode.modules.common.vo.AdminStaffVo;
import com.wingscode.modules.common.vo.AdminWorkerVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return new PageUtils(baseMapper.selectCustomerList(pages, parentId, date1, date2));
    }

    @Override
    public PageUtils allWorker(Map<String, Object> params) {
        Integer page = Integer.parseInt((String) params.get("page"));
        Integer limit = Integer.parseInt((String) params.get("limit"));
        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");
        Long parentId = Long.parseLong(org.springframework.util.StringUtils.isEmpty(params.get("parentId")) ? "0" : (String) params.get("parentId"));
        Page<AdminWorkerVO> pages = new Page<>(page, limit);
        return new PageUtils(baseMapper.allWorker(pages, parentId, date1, date2));
    }

    @Override
    public List<AdminWorkerVO> allWorkerExcel(Map<String, Object> params) {
        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");
        Long parentId = Long.parseLong(org.springframework.util.StringUtils.isEmpty(params.get("parentId")) ? "0" : (String) params.get("parentId"));
        return baseMapper.allWorkerExcel(parentId, date1, date2);
    }

    @Override
    public PageUtils allWorker1(Map<String, Object> params) {
        Integer page = Integer.parseInt((String) params.get("page"));
        Integer limit = Integer.parseInt((String) params.get("limit"));
        String date = (String) params.get("date");
        Long parentId = Long.parseLong(org.springframework.util.StringUtils.isEmpty(params.get("parentId")) ? "0" : (String) params.get("parentId"));
        Page<AdminWorkerVO> pages = new Page<>(page, limit);
        return new PageUtils(baseMapper.allWorker1(pages, parentId, date));

        }

    @Override
    public PageUtils allStaff(Map<String, Object> params) {
         //swagger测试使用
        // Gson gson = new Gson();
        // Map<String, Object> map = new HashMap<String, Object>();
        // map = gson.fromJson((String) params.get("params"), map.getClass());
        // Integer page =  Integer.parseInt((String) map.get("page"));
        // Integer limit = Integer.parseInt((String) map.get("limit"));
        // Long  userId =Long.parseLong((String) map.get("userId"));
        // Long userId1 = Long.parseLong(org.springframework.util.StringUtils.isEmpty(userId) ? "0" : String.valueOf(userId));
        // Page<AdminStaffVo> pages = new Page<>(page, limit);
        // return new PageUtils(baseMapper.allStaff(pages,userId1,date1,date2,like));


        String date1 = (String) params.get("date1");
        String date2 = (String) params.get("date2");
        Integer page = Integer.parseInt((String) params.get("page"));
        Integer limit = Integer.parseInt((String) params.get("limit"));
        Long userId= (Long) params.get("userId");
        String like=null;
        if(StringUtils.isNotEmpty((String)params.get("key"))){
            like =(String)params.get("key") ;
        }

       Long userId1 = Long.parseLong(org.springframework.util.StringUtils.isEmpty(userId) ? "0" :String.valueOf(userId));

        Page<AdminStaffVo> pages = new Page<>(page, limit);
        return new PageUtils(baseMapper.allStaff(pages,userId1,date1,date2,like));
    }

}



