package com.haibin.myzhxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haibin.myzhxy.mapper.GradeMapper;
import com.haibin.myzhxy.pojo.Grade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("grService")
@Transactional
public class GradeService extends ServiceImpl<GradeMapper, Grade> implements com.haibin.myzhxy.service.GradeService {
}
