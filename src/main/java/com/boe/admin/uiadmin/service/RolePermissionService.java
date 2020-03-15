package com.boe.admin.uiadmin.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boe.admin.uiadmin.dao.RolePermissionMapper;
import com.boe.admin.uiadmin.po.RolePermissionPo;


@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermissionPo> implements IService<RolePermissionPo> {

}