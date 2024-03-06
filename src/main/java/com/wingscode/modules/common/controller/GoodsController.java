package com.wingscode.modules.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.modules.common.entity.GoodsEntity;
import com.wingscode.modules.common.entity.TypeEntity;
import com.wingscode.modules.common.service.GoodsService;
import com.wingscode.modules.common.service.TypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;




/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-02-11 21:11:16
 */
@RestController
@RequestMapping("generator/goods")
@Api("商品")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private TypeService typeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:goods:list")
    @ApiOperation("查询")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:goods:info")
    public R info(@PathVariable("id") Long id){
		GoodsEntity goods = goodsService.getById(id);
		if (null !=goods){
           TypeEntity type = typeService.getById(goods.getTypeId());
           goods.setTypeName(type.getName());
        }
        return R.ok().put("goods", goods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:goods:save")
    public R save(@RequestBody GoodsEntity goods){
		goodsService.save(goods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:goods:update")
    public R update(@RequestBody GoodsEntity goods){
		goodsService.updateById(goods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:goods:delete")
    public R delete(@RequestBody Long[] ids){
		goodsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/selectAll")
    public R selectAll(){
        List<GoodsEntity> goodsList =goodsService.list(
                new QueryWrapper<GoodsEntity>()
        );
        return R.ok().put("goodsList", goodsList);
    }
}
