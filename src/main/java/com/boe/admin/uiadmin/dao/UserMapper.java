package com.boe.admin.uiadmin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boe.admin.uiadmin.po.PermissionPo;
import com.boe.admin.uiadmin.po.UserPo;


@Repository
public interface UserMapper extends BaseMapper<UserPo> {
	
	
	/**根据用户id查询用户所有权限*/
	List<PermissionPo> selectPermissionsByUserId(@Param("userId")Long userId);
}
