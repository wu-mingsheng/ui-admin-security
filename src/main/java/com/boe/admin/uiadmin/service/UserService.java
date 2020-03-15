package com.boe.admin.uiadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boe.admin.uiadmin.dao.UserMapper;
import com.boe.admin.uiadmin.po.PermissionPo;
import com.boe.admin.uiadmin.po.UserPo;


@Service
public class UserService extends ServiceImpl<UserMapper, UserPo> implements IService<UserPo> {
	
	@Autowired
	private UserMapper userMapper;

	
    public List<PermissionPo> selectPermissionsByUserId(Long userId) {
    	
        return userMapper.selectPermissionsByUserId(userId);
    }

}