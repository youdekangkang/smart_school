package com.haibin.myzhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haibin.myzhxy.pojo.LoginForm;
import com.haibin.myzhxy.pojo.Teacher;

public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);
}
