package com.logistics.persistence.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logistics.persistence.dao.RepairCategoryDao;
import com.logistics.persistence.model.RepairCategory;
import com.logistics.persistence.model.RepairCategoryExample;
@Component
public class RepairCategoryManager extends BaseManager<RepairCategoryDao> {
	@Autowired
	private RepairCategoryDao repairCategoryDao;

	@Override
	public RepairCategoryDao getDao() {
		return repairCategoryDao;
	}

	public RepairCategory getByKey(Integer id) {
		return repairCategoryDao.getMapper().selectByPrimaryKey(id);
	}

	public int insert(RepairCategory repairCategory) {
		return repairCategoryDao.getMapper().insert(repairCategory);
	}

	public List<RepairCategory> getRepairCategoryList() {
		RepairCategoryExample example = new RepairCategoryExample();
		return repairCategoryDao.getMapper().selectByExample(example);
	}

	public int update(RepairCategory repairCategory) {
		return repairCategoryDao.getMapper().updateByPrimaryKey(repairCategory);
	}

	public int deleteBykey(int id) {
		return repairCategoryDao.getMapper().deleteByPrimaryKey(id);
	}

	public int deleteByName(String name) {
		RepairCategoryExample example = new RepairCategoryExample();
		example.createCriteria().andNameEqualTo(name);
		return repairCategoryDao.getMapper().deleteByExample(example);
	}

	public List<RepairCategory> getByName(String name) {
		RepairCategoryExample example = new RepairCategoryExample();
		example.createCriteria().andNameEqualTo(name);
		return repairCategoryDao.getMapper().selectByExample(example);
	}
}
