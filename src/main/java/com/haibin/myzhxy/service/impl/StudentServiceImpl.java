package com.haibin.myzhxy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haibin.myzhxy.mapper.StudentMapper;
import com.haibin.myzhxy.pojo.Admin;
import com.haibin.myzhxy.pojo.LoginForm;
import com.haibin.myzhxy.pojo.Student;
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
}
