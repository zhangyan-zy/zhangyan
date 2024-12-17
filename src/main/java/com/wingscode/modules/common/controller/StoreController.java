package com.wingscode.modules.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.modules.common.entity.StoreEntity;
import com.wingscode.modules.common.service.StoreService;
import com.wingscode.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * 
 *
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2024-12-12 19:10:29
 */
@RestController
@RequestMapping("generator/store")
@Api("商店")
public class StoreController extends AbstractController {
    @Autowired
    private StoreService storeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:store:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = storeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:store:info")
    public R info(@PathVariable("id") Integer id){
		StoreEntity store = storeService.getById(id);

        return R.ok().put("store", store);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:store:save")
    public R save(@RequestBody StoreEntity store){
        store.setCreatetime(new Date());
		storeService.save(store);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:store:update")
    public R update(@RequestBody StoreEntity store){
		storeService.updateById(store);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:store:delete")
    public R delete(@RequestBody Integer[] ids){
		storeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    @RequestMapping("/selectAll")
    @RequiresPermissions("generator:store:list")
    public R selectAll(){
        List<StoreEntity> storeEntity =storeService.list(
                new QueryWrapper<StoreEntity>()
        );
        return R.ok().put("list", storeEntity);
    }
}
