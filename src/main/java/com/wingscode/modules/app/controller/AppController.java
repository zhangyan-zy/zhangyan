package com.wingscode.modules.app.controller;


import com.wingscode.common.utils.R;
import com.wingscode.modules.common.service.StatisticalService;
import com.wingscode.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * APP测试接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api("APP接口")
public class AppController   extends AbstractController {

    @Autowired
    private StatisticalService statisticalService;


    /**
     * 客户leads查询
     */
    @PostMapping("/appcoustomerLeadersList")
    @ApiOperation("所有客户的统计情况")
    public R UserList(@RequestParam Map<String, Object> params) {
        Map map =new HashMap();
        map.put("parentId","40");
        map.put("date1","2020-3-5");
        map.put("date2","2020-3-8");
        map.put("page","1");
        map.put("limit","4");
        return R.ok().put("user", statisticalService.selectCustomerList(map));
    }


    /**
     * 坐席排序
     */
    @PostMapping("/AgentsLeadersList")
    @ApiOperation("所有leads排序的统计情况")
    public R AgentList(@RequestParam  Map<String, Object> params) {
        Map map =new HashMap();
        map.put("parentId","40");
        map.put("date1","2020-3-5");
        map.put("date2","2020-3-8");
        map.put("page","1");
        map.put("limit","12");
        return R.ok().put("user", statisticalService.selectAgentList(map));
    }


}
