package com.haibin.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haibin.myzhxy.mapper.TeacherMapper;
import com.haibin.myzhxy.pojo.LoginForm;
import com.haibin.myzhxy.pojo.Teacher;
import com.haibin.myzhxy.service.TeacherService;
import com.haibin.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    @Override
    public IPage<Teacher> getTeachersByOpr(Page<Teacher> pageParam, Teacher teacher) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(teacher != null){
            //班级名称条件
            String clazzName = teacher.getClazzName();
            if (!StringUtils.isEmpty(clazzName)) {
                queryWrapper.eq("clazz_name",clazzName);
            }
            //教师名称条件
            String teacherName = teacher.getName();
            if(!StringUtils.isEmpty(teacherName)){
                queryWrapper.like("name",teacherName);
            }
            queryWrapper.orderByDesc("id");
            queryWrapper.orderByAsc("name");
        }

        IPage<Teacher> page = baseMapper.selectPage(pageParam, queryWrapper);

        return page;
    }
}
