package com.logistics.persistence.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logistics.persistence.mapper.UserInfoMapper;
import com.logistics.persistence.model.UserInfo;
import com.logistics.persistence.model.UserInfoExample;
@Component
public class UserInfoDao extends BaseDAO<UserInfo, Long, UserInfoMapper, UserInfoExample>{
	@Autowired
	private UserInfoMapper mapper;
	@Override
	public UserInfoMapper getMapper() {
		return mapper;
	}
	


}
