package com.wingscode.modules.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.modules.common.entity.GoodsEntity;
import com.wingscode.modules.common.entity.TypeEntity;
import com.wingscode.modules.common.service.GoodsService;
import com.wingscode.modules.common.service.TypeService;
import com.wingscode.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;




/**
 * 
 *
 * @author zhangyan
 * @email sunlightcs@gmail.com
 * @date 2023-02-11 21:11:16
 */
@RestController
@RequestMapping("generator/goods")
@Api("商品")
public class GoodsController extends AbstractController {
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
    public R list(@RequestParam Map<String, Object> params) throws Exception {
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
        goods.setState(2);
        goods.setCreatetime(new Date());
		goodsService.save(goods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:goods:update")
    public R update(@RequestBody GoodsEntity goods){
        if (goods.getImageUrl().contains("token")){
            GoodsEntity byId = goodsService.getById(goods.getId());
            goods.setImageUrl(byId.getImageUrl());
        }
        if (null ==goods) return R.error("系统错误！联系管理员");
        if (goods.getCount()<0) return R.error("数量不能为负数");
        if (goods.getPrice().compareTo(new BigDecimal("0"))<0) return R.error("数量不能为0或负数");
        if (goods.getWeight()<0) return R.error("重量不能为负数");
        goodsService.updateById(goods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:goods:delete")
    public R delete(@RequestBody Long[] ids){
        for (int i=0;i<ids.length;i++){
           GoodsEntity goods = goodsService.getById(ids[i]);
           goods.setState(1);
           goodsService.updateById(goods);
        }
        return R.ok();
    }


    /**
     * 查询所有
     */
    @RequestMapping("/selectAll")
    public R selectAll(){
        List<GoodsEntity> goodsList =goodsService.list(
                new QueryWrapper<GoodsEntity>()
                .eq("State", "0")
        );
        return R.ok().put("goodsList", goodsList);
    }

    /**
     * 审核
     */
    @RequestMapping("/review")
    @RequiresPermissions("generator:goods:audit")
    public R review(@RequestBody Long[] ids){
        for (int i=0;i<ids.length;i++) {
            GoodsEntity goods = goodsService.getById(ids[i]);
            if (null ==goods) return R.error("系统错误！联系管理员");
            if (goods.getState()==2) {
                goods.setState(0);
                goodsService.updateById(goods);
                continue;
            }
            if (goods.getState()==0){
                goods.setState(1);
                goodsService.updateById(goods);
                continue;
            }
            if (goods.getState()==1){
                goods.setState(0);
                goodsService.updateById(goods);
                continue;
            }
        }
        return R.ok();
    }
}
