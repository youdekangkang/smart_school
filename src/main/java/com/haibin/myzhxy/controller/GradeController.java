package com.haibin.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haibin.myzhxy.pojo.Grade;
import com.haibin.myzhxy.service.GradeService;
import com.haibin.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "年级控制器")
@RestController      //不是@Controller 这里需要使用异步交互
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeServicel;

    @ApiOperation("删除grade信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(
            @ApiParam("要删除的所有的grade的id的json集合") @RequestBody List<Integer> ids
    ){
        gradeServicel.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("新增或修改grade，有ID属性则是修改，若无则为增加")
    @GetMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(
        @ApiParam("JSON的Grade对象") @RequestBody Grade grade
    ){
        //接受参数
        //调用服务层方法完成增减或者修改
        gradeServicel.saveOrUpdate(grade);
        return Result.ok();
    }

    @ApiOperation("根据年级名称模糊查询，带分页")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(
        @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
        @ApiParam("分页查询的页大小") @PathVariable("pageSize") Integer pageSize,
        @ApiParam("分页查询模糊匹配的名称") String gradeName
    ){
        //分页 待条件查询
        Page<Grade> page = new Page<Grade>(pageNo,pageSize);
        // 通过服务层
        IPage<Grade> pageRs = gradeServicel.getGradeByOpr(page,gradeName);


        // 封装Result并返回
        return Result.ok(pageRs);
    }

}
