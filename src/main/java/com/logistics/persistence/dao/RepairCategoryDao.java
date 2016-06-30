package com.logistics.persistence.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logistics.persistence.mapper.RepairCategoryMapper;
import com.logistics.persistence.model.RepairCategory;
import com.logistics.persistence.model.RepairCategoryExample;
@Component
public class RepairCategoryDao extends BaseDAO<RepairCategory, Integer, RepairCategoryMapper, RepairCategoryExample>{
	@Autowired
	private RepairCategoryMapper mapper;
	@Override
	public RepairCategoryMapper getMapper() {
		return mapper;
	}

}
