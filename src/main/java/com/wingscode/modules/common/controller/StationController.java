package com.wingscode.modules.common.controller;

import com.wingscode.common.utils.PageUtils;
import com.wingscode.common.utils.R;
import com.wingscode.common.validator.ValidatorUtils;
import com.wingscode.modules.common.entity.StationEntity;
import com.wingscode.modules.common.service.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;




/**
 * 站点表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-04-25 13:52:55
 */
@RestController
@RequestMapping("common/station")
@Api("站点")
public class StationController {
    @Autowired
    private StationService stationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:station:list")
    @ApiOperation("列表")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stationService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 不带分页的list
     */
    @RequestMapping("/AllList")
    @ApiOperation("列表")
    public R AllList(){
      List<StationEntity> list= stationService.queryAll();
        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:station:info")
    @ApiOperation("信息")
    public R info(@PathVariable("id") Long id){
        StationEntity station = stationService.getById(id);

        return R.ok().put("station", station);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:station:save")
    @ApiOperation("保存")
    public R save(@RequestBody StationEntity station){
        station.setGmtCreate(new Date());
        station.setGmtModify(new Date());
        stationService.save(station);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:station:update")
    @ApiOperation("修改")
    public R update(@RequestBody StationEntity station){
        station.setGmtModify(new Date());
        ValidatorUtils.validateEntity(station);
        stationService.updateById(station);
        
        return R.ok();
    }

    // /**
    //  * 删除
    //  */
    // @RequestMapping("/delete")
    // @RequiresPermissions("sys:station:delete")
    // public R delete(@RequestBody Long[] ids){
    //     stationService.removeByIds(Arrays.asList(ids));
    //
    //     return R.ok();
    // }

}
