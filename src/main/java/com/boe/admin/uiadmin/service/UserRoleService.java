package com.boe.admin.uiadmin.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boe.admin.uiadmin.dao.UserRoleMapper;
import com.boe.admin.uiadmin.po.UserRolePo;


@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRolePo> implements IService<UserRolePo> {

}