package com.logistics.persistence.dao;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.logistics.persistence.mapper.PlacardMapper;
import com.logistics.persistence.model.Placard;
import com.logistics.persistence.model.PlacardExample;
@Component
public class PlacardDao extends BaseDAO<Placard, Long, PlacardMapper, PlacardExample>{
	@Autowired
	private PlacardMapper mapper;
	@Override
	public PlacardMapper getMapper() {
		return mapper;
	}
	
	
	


}
