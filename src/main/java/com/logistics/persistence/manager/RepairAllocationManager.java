package com.logistics.persistence.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logistics.persistence.dao.RepairAllocationDao;
import com.logistics.persistence.model.RepairAllocation;
@Component
public class RepairAllocationManager extends BaseManager<RepairAllocationDao> {
	@Autowired
	private RepairAllocationDao repairAllocationDao;

	@Override
	public RepairAllocationDao getDao() {
		return repairAllocationDao;
	}

	public int insert(RepairAllocation repairAllocation) {
		return repairAllocationDao.getMapper().insert(repairAllocation);
	}
}
