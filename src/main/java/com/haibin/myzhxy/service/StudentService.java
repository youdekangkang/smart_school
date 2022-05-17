package com.haibin.myzhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haibin.myzhxy.pojo.LoginForm;
import com.haibin.myzhxy.pojo.Student;

public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);

    Student getStudentById(Long userId);
}
