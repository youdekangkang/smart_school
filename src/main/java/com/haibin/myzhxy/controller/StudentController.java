package com.haibin.myzhxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haibin.myzhxy.pojo.Student;
import com.haibin.myzhxy.service.StudentService;
import com.haibin.myzhxy.util.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController      //不是@Controller 这里需要使用异步交互
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentServicel;

    @ApiOperation("查询学生信息,分页带条件")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentsByOpr(
            @ApiParam("页码数") @PathVariable("pageNo")Integer pageNo,
            @ApiParam("页大小") @PathVariable("pageSize")Integer pageSize,
            @ApiParam("查询条件转换后端数据模型") Student student
    ){
        // 准备分页信息封装的page对象
        Page<Student> page =new Page<>(pageNo,pageSize);
        // 获取分页的学生信息
        IPage<Student> iPage = studentServicel.getStudentByOpr(page, student);
        // 返回学生信息
        return Result.ok(iPage);
    }

}
