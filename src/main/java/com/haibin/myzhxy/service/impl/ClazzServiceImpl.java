package com.haibin.myzhxy.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haibin.myzhxy.mapper.ClazzMapper;
import com.haibin.myzhxy.pojo.Clazz;
import com.haibin.myzhxy.service.ClazzService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("clazzServiceImpl")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    /**
     * 分页查询所有班级信息【带条件】
     * @param clazz
     * @return
     */
    @Override
    public IPage<Clazz> getClazzsByOpr(Page<Clazz> pageParam,Clazz clazz) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(clazz != null){
            //年级名称条件
            String gradeName = clazz.getGrade_name();
            if(!StringUtils.isEmpty(gradeName)){
                queryWrapper.eq("grade_name",gradeName);
            }
            //班级名称条件
            String clazzName = clazz.getName();
            if(!StringUtils.isEmpty(clazzName)){
                queryWrapper.like("name",clazzName);
            }
            queryWrapper.orderByDesc("id");
            queryWrapper.orderByAsc("name");
        }
        Page page = baseMapper.selectPage(pageParam, queryWrapper);
        return page;
    }

    @Override
    public List<Clazz> getClazzs() {
        return baseMapper.selectList(null);
    }
}
