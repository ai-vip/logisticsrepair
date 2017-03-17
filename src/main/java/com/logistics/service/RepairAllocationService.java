package com.logistics.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logistics.persistence.manager.RepairAllocationManager;
import com.logistics.persistence.manager.RepairOrderManager;
import com.logistics.persistence.manager.UserManager;
import com.logistics.persistence.model.RepairAllocation;
import com.logistics.persistence.model.RepairOrder;
import com.logistics.persistence.model.User;

import com.logistics.common.utils.ValidateUtils;
import com.logistics.common.utils.exception.ServiceException;
import com.logistics.common.utils.response.Res;

@Service
public class RepairAllocationService {
	@Autowired
	private RepairAllocationManager repairAllocationManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private RepairOrderManager repairOrderManager;

	@Transactional
	public Res allotRepairOrder(String orderId, String userId, Long optUserId) {
		Res res = new Res();
		try {
			if(!ValidateUtils.isLong(userId)){
				throw new ServiceException("用户id错误");
			}
			if(!ValidateUtils.isLong(orderId)){
				throw new ServiceException("维修单id错误");
			}
			RepairOrder repairOrder = repairOrderManager.getByKey(Long.parseLong(orderId));
			User repairMan = userManager.getRepairMan(Long.parseLong(userId));
			
			if(repairOrder==null){
				throw new ServiceException("此维修单不存在");
			}
			if(repairOrder.getStatus()!=1){
				throw new ServiceException("此维修单已分配");
			}
			if(repairMan==null){
				throw new ServiceException("此维修人员不存在");
			}
			if(repairMan.getGroupId()!=2){
				throw new ServiceException("此用户不是维修人员");
			}
			
			repairOrder.setStatus(2);
			repairOrder.setRepairUserId(repairMan.getUsrId());
			repairOrder.setOptUserId(optUserId);
			repairOrder.setUpdateTime(new Date());
			repairOrderManager.update(repairOrder);
			
			RepairAllocation repairAllocation = new RepairAllocation();
			repairAllocation.setRepairId(repairOrder.getId());
			repairAllocation.setRepairUserId(repairMan.getUsrId());
			repairAllocation.setUpdateTime(new Date());
			repairAllocation.setCreateTime(new Date());
			repairAllocationManager.insert(repairAllocation);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}
}
