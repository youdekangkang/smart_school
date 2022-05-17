package com.haibin.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haibin.myzhxy.mapper.TeacherMapper;
import com.haibin.myzhxy.pojo.LoginForm;
import com.haibin.myzhxy.pojo.Teacher;
import com.haibin.myzhxy.service.TeacherService;
import com.haibin.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("teacherServiceImpl")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        //这里获取的密码为明文 需要MD5加密后转换为密文在数据库中比对


        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public Teacher getTeacherById(Long userId) {

        QueryWrapper queryWrapper = new QueryWrapper<Teacher>();
        queryWrapper.eq("id",userId);
        return baseMapper.selectOne(queryWrapper);

    }
}
