package com.haibin.myzhxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haibin.myzhxy.pojo.Clazz;
import com.haibin.myzhxy.service.ClazzService;
import com.haibin.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "班级管理器")
@RestController      //不是@Controller 这里需要使用异步交互
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzServicel;

    @ApiOperation("查询全部班级信息")
    @RequestMapping("/getClazzs")
    public Result getClazz(){
        List<Clazz> clazzes = clazzServicel.getClazzs();
        return Result.ok();
    }

    @ApiOperation("删除单个或多个班级的方法")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(
            @ApiParam("多个班级id的JSON") @RequestBody List<Integer> ids
    ){
        clazzServicel.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("增加或修改班级管理信息")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(
            @ApiParam("JSON格式的班级管理信息") @RequestBody Clazz clazz
    ){
        clazzServicel.saveOrUpdate(clazz);
        return Result.ok();
    }

    @ApiOperation("查询班级信息,分页带条件")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(
            @ApiParam("页码数")  @PathVariable("pageNo") Integer pageNo,
            @ApiParam("页大小")  @PathVariable("pageSize") Integer pageSize,
            @ApiParam("查询条件") Clazz clazz
    ){
        //设置分页信息
        Page<Clazz> page =new Page<>(pageNo,pageSize);
        IPage<Clazz> iPage = clazzServicel.getClazzsByOpr(page, clazz);
        return Result.ok(iPage);
    }
}
