package com.wingscode.modules.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.modules.common.entity.TypeEntity;
import com.wingscode.modules.common.service.TypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;




/**
 * 商品类别
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-02-11 21:48:22
 */
@RestController
@RequestMapping("generator/type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:type:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = typeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:type:info")
    public R info(@PathVariable("id") Integer id){
		TypeEntity type = typeService.getById(id);

        return R.ok().put("type", type);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:type:save")
    public R save(@RequestBody TypeEntity type){
		typeService.save(type);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:type:update")
    public R update(@RequestBody TypeEntity type){
		typeService.updateById(type);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:type:delete")
    public R delete(@RequestBody Integer[] ids){
		typeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/selectAll")
    public R selectAll(){
        List<TypeEntity> typeEntity =typeService.list(
                new QueryWrapper<TypeEntity>()
        );
        return R.ok().put("list", typeEntity);
    }
}
