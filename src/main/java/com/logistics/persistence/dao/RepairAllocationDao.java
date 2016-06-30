package com.logistics.persistence.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logistics.persistence.mapper.RepairAllocationMapper;
import com.logistics.persistence.model.RepairAllocation;
import com.logistics.persistence.model.RepairAllocationExample;
@Component
public class RepairAllocationDao extends BaseDAO<RepairAllocation, Long, RepairAllocationMapper, RepairAllocationExample>{
	@Autowired
	private RepairAllocationMapper mapper;
	@Override
	public RepairAllocationMapper getMapper() {
		return mapper;
	}
	
	public List<RepairAllocation> list(){
		RepairAllocationExample example=new RepairAllocationExample();
		return mapper.selectByExample(example);
	}

}
