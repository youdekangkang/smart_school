package com.haibin.myzhxy.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haibin.myzhxy.mapper.ClazzMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.haibin.myzhxy.pojo.Clazz;

@Service("clService")
@Transactional
public class ClazzService extends ServiceImpl<ClazzMapper,Clazz> implements com.haibin.myzhxy.service.ClazzService {
}
