package com.haibin.myzhxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haibin.myzhxy.mapper.AdminMapper;
import com.haibin.myzhxy.pojo.Admin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Springboot使用服务层
@Service("adService")
@Transactional
public class AdminService extends ServiceImpl<AdminMapper, Admin> implements com.haibin.myzhxy.service.AdminService {
}
