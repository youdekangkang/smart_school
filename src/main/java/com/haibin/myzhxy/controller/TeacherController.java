package com.haibin.myzhxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haibin.myzhxy.pojo.Teacher;
import com.haibin.myzhxy.service.TeacherService;
import com.haibin.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "教师信息管理控制器")
@RestController      //不是@Controller 这里需要使用异步交互
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherServicel;

    @ApiOperation("删除一个或者多个教师信息")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
            @RequestBody List<Integer> ids
    ){
        teacherServicel.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("添加和修改教师信息")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
            @RequestBody Teacher teacher
    ){
        teacherServicel.saveOrUpdate(teacher);
        return Result.ok();
    }

    @ApiOperation("获取教师信息,分页带条件")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            Teacher teacher
    ){
        Page<Teacher> pageParam = new Page<>(pageNo,pageSize);
        IPage<Teacher> page = teacherServicel.getTeachersByOpr(pageParam, teacher);
        return Result.ok(page);
    }

}
