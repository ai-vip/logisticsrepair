package com.logistics.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logistics.persistence.manager.RepairCategoryManager;
import com.logistics.persistence.model.RepairCategory;

import com.logistics.common.utils.ValidateUtils;
import com.logistics.common.utils.exception.ServiceException;
import com.logistics.common.utils.response.Res;

@Service
public class RepairCategoryService {
	@Autowired
	private RepairCategoryManager repairCategoryManager;

	@Transactional
	public Res addRepairCategory(String name, String des) {
		Res res = new Res();
		try {
			if (ValidateUtils.isNull(name)) {
				throw new ServiceException("名称不能为空");
			}
			List<RepairCategory> test = repairCategoryManager.getByName(name);
			if(test==null||test.isEmpty()){
				RepairCategory repairCategory = new RepairCategory();
				
				repairCategory.setName(name);
				if (!ValidateUtils.isNull(des)) {
					repairCategory.setDes(des);
				}
				repairCategory.setCreateTime(new Date());
				repairCategory.setUpdateTime(new Date());
				repairCategoryManager.insert(repairCategory);
			}

		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}
	
	public Res getRepairCategoryList() {
		Res res = new Res();
		try {
			List<RepairCategory> repairCategoryList = repairCategoryManager.getRepairCategoryList();
			res.addRespose("repairCategoryList", repairCategoryList);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	public Res updateRepairCategory(String id, String name, String des) {
		Res res = new Res();
		try {
			if(!ValidateUtils.isInt(id)){
				throw new ServiceException("分类id错误");
			}
			RepairCategory repairCategory = repairCategoryManager.getByKey(Integer.parseInt(id));
			
			if (!ValidateUtils.isNull(name)) {
				repairCategory.setName(name);
			}
			if (!ValidateUtils.isNull(des)) {
				repairCategory.setDes(des);
			}
			
			repairCategory.setUpdateTime(new Date());
			repairCategoryManager.update(repairCategory);

		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	public Res deleteRepairCategory(String id) {
		Res res = new Res();
		try {
			if(!ValidateUtils.isInt(id)){
				throw new ServiceException("分类id错误");
			}
			repairCategoryManager.deleteBykey(Integer.parseInt(id));

		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	public Res removeRepairCategory(String name) {
		Res res = new Res();
		try {
			if(ValidateUtils.isNull(name)){
				throw new ServiceException("分类名不能为空");
			}
			repairCategoryManager.deleteByName(name);

		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}
}
