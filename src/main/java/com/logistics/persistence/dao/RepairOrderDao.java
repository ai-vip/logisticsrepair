package com.logistics.persistence.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logistics.persistence.mapper.RepairOrderMapper;
import com.logistics.persistence.model.RepairOrder;
import com.logistics.persistence.model.RepairOrderExample;
@Component
public class RepairOrderDao extends BaseDAO<RepairOrder, Long, RepairOrderMapper, RepairOrderExample>{
	@Autowired
	private RepairOrderMapper mapper;
	@Override
	public RepairOrderMapper getMapper() {
		return mapper;
	}
	
	public List<RepairOrder> list(){
		RepairOrderExample example=new RepairOrderExample();
		return mapper.selectByExample(example);
	}

}
