package com.logistics.persistence.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logistics.persistence.mapper.UserMapper;
import com.logistics.persistence.model.User;
import com.logistics.persistence.model.UserExample;
@Component
public class UserDao extends BaseDAO<User, Long, UserMapper, UserExample>{
	@Autowired
	private UserMapper mapper;
	@Override
	public UserMapper getMapper() {
		return mapper;
	}
	
	
	


}
