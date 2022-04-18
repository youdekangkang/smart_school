package com.haibin.myzhxy.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haibin.myzhxy.mapper.StudentMapper;
import com.haibin.myzhxy.pojo.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("stService")
@Transactional
public class StudentService extends ServiceImpl<StudentMapper, Student> implements com.haibin.myzhxy.service.StudentService {
}
