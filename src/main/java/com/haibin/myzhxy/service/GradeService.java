package com.haibin.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haibin.myzhxy.pojo.Grade;

import java.util.List;

public interface GradeService extends IService<Grade> {
    IPage<Grade> getGradeByOpr(Page<Grade> pageParam, String gradeName);

    List<Grade> getGrades();
}
