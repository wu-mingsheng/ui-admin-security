package com.boe.admin.uiadmin.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boe.admin.uiadmin.dao.PermissionMapper;
import com.boe.admin.uiadmin.po.PermissionPo;


@Service
public class PermissionService extends ServiceImpl<PermissionMapper, PermissionPo> implements IService<PermissionPo> {

}