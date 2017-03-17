package com.logistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logistics.persistence.manager.PlacardManager;
import com.logistics.persistence.manager.RepairCategoryManager;
import com.logistics.persistence.manager.RepairOrderManager;
import com.logistics.persistence.model.Placard;
import com.logistics.persistence.model.RepairCategory;
import com.logistics.persistence.model.RepairOrder;

import com.logistics.common.utils.exception.ServiceException;
import com.logistics.common.utils.response.Res;

@Service
public class IndexService {
	@Autowired
	private PlacardManager placardManager;
	@Autowired
	private RepairCategoryManager repairCategoryManager; 
	@Autowired
	private RepairOrderManager repairOrderManager;
	
	public Res getIndexInfo() {
		Res res = new Res();
		try {
			//数量统计
			int totalOrderCount=repairOrderManager.getOrderCount();
			int needRepairOrderCount=repairOrderManager.getOrderCount(1);
			int haveRepairOrderCount=repairOrderManager.getOrderCount(2)+repairOrderManager.getOrderCount(3);
			//维修分类
			List<RepairCategory> repairCategoryList = repairCategoryManager.getRepairCategoryList();
			//维修单
			List<RepairOrder> repairOrderList = repairOrderManager.getRepairOrderList(null);
			//最新公告
			List<Placard> placardList = placardManager.getPlacardList();
			Placard lastedPlacard=null;
			if(placardList!=null&&placardList.size()>0){
				lastedPlacard=placardList.get(0);
			}
			res.addRespose("totalOrderCount", totalOrderCount);
			res.addRespose("needRepairOrderCount", needRepairOrderCount);
			res.addRespose("haveRepairOrderCount", haveRepairOrderCount);
			res.addRespose("repairCategoryList", repairCategoryList);
			res.addRespose("repairOrderList", repairOrderList);
			res.addRespose("lastedPlacard", lastedPlacard);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	} 
}
