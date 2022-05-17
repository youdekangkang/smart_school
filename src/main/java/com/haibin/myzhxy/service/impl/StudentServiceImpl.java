package com.haibin.myzhxy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haibin.myzhxy.mapper.StudentMapper;
import com.haibin.myzhxy.pojo.Admin;
import com.haibin.myzhxy.pojo.LoginForm;
import com.haibin.myzhxy.pojo.Student;
import com.haibin.myzhxy.pojo.Teacher;
import com.haibin.myzhxy.service.StudentService;
import com.haibin.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("studentServiceImpl")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        //这里获取的密码为明文 需要MD5加密后转换为密文在数据库中比对


        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public Student getStudentById(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper<Student>();
        queryWrapper.eq("id",userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    /**
     * 按条件查询学生信息【带分页】
     */
    public IPage<Student> getStudentByOpr(Page<Student> pageParam,Student student){
        QueryWrapper<Student> queryWrapper = null;
        if(student != null) {
            queryWrapper = new QueryWrapper<>();
            if (student.getClazzName() != null) {
                queryWrapper.eq("clazz_name", student.getClazzName());
            }
            if (student.getName() != null) {
                queryWrapper.like("name", student.getName());
            }
            queryWrapper.orderByDesc("id");
            queryWrapper.orderByAsc("name");
        }
        //创建分页对象
        IPage<Student> pages = baseMapper.selectPage(pageParam, queryWrapper);

        return pages;
    }

}
