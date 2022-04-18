package com.haibin.myzhxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haibin.myzhxy.mapper.TeacherMapper;
import com.haibin.myzhxy.pojo.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("teService")
@Transactional
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> implements com.haibin.myzhxy.service.TeacherService {
}
