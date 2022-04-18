package com.haibin.myzhxy.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.haibin.myzhxy.pojo.Admin;
import com.haibin.myzhxy.pojo.LoginForm;

public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginForm);
}
