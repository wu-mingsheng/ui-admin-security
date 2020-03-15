package com.boe.admin.uiadmin.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boe.admin.uiadmin.dao.RoleMapper;
import com.boe.admin.uiadmin.po.RolePo;


@Service
public class RoleService extends ServiceImpl<RoleMapper, RolePo> implements IService<RolePo> {

}