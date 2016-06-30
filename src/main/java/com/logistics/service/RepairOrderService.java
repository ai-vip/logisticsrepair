package com.logistics.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logistics.persistence.manager.RepairCategoryManager;
import com.logistics.persistence.manager.RepairOrderManager;
import com.logistics.persistence.model.RepairCategory;
import com.logistics.persistence.model.RepairOrder;
import com.logistics.persistence.model.User;

import common.Page;
import common.utils.ValidateUtils;
import common.utils.exception.ServiceException;
import common.utils.response.Res;

@Service
public class RepairOrderService {
	@Autowired
	private RepairOrderManager repairOrderManager;
	@Autowired
	private RepairCategoryManager repairCategoryManager;

	public Res queryRepairOrderList(String title, String status, String timeRange, Page page) {
		Res res = new Res();
		try {
			List<RepairOrder> repairOrderList = repairOrderManager.queryRepairOrderList(title, status, timeRange, page);
			res.addRespose("page", page);
			res.addRespose("repairOrderList", repairOrderList);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	@Transactional
	public Res addOrder(String title, String des, String phone, String address, String categoryId, Long usrId) {
		Res res = new Res();
		try {
			if (ValidateUtils.isNull(title)) {
				throw new ServiceException("标题不能为空");
			}
			if (ValidateUtils.isNull(des)) {
				throw new ServiceException("维修描述不能为空");
			}
			if (ValidateUtils.isNull(phone)) {
				throw new ServiceException("联系电话不能为空");
			}
			if (ValidateUtils.isNull(address)) {
				throw new ServiceException("地址不能为空");
			}
			if (!ValidateUtils.isInt(categoryId)) {
				throw new ServiceException("维修分类错误");
			}
			RepairCategory repairCategory = repairCategoryManager.getByKey(Integer.parseInt(categoryId));
			if (repairCategory == null) {
				throw new ServiceException("维修分类不存在");
			}
			RepairOrder repairOrder = new RepairOrder();
			repairOrder.setTitle(title);
			repairOrder.setDes(des);
			repairOrder.setPhone(phone);
			repairOrder.setAddress(address);
			repairOrder.setSubmitUserId(usrId);
			repairOrder.setOptUserId(usrId);
			repairOrder.setStatus(1);
			repairOrder.setCategoryId(repairCategory.getId());
			repairOrder.setCategoryName(repairCategory.getName());
			repairOrder.setUpdateTime(new Date());
			repairOrder.setCreateTime(new Date());
			repairOrderManager.insert(repairOrder);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	public Res updateOrder(String orderId, String title, String des, String phone, String address, String categoryId) {
		Res res = new Res();
		try {
			if (!ValidateUtils.isLong(orderId)) {
				throw new ServiceException("维修单id错误");
			}
			RepairOrder repairOrder = repairOrderManager.getByKey(Long.parseLong(orderId));
			if (repairOrder == null) {
				throw new ServiceException("维系单不存在");
			}

			if (!ValidateUtils.isNull(title)) {
				repairOrder.setTitle(title);
			}
			if (!ValidateUtils.isNull(des)) {
				repairOrder.setDes(des);
			}
			if (!ValidateUtils.isNull(phone)) {
				repairOrder.setPhone(phone);
			}
			if (!ValidateUtils.isNull(address)) {
				repairOrder.setAddress(address);
			}
			if (ValidateUtils.isInt(categoryId)) {
				RepairCategory repairCategory = repairCategoryManager.getByKey(Integer.parseInt(categoryId));
				if (repairCategory != null) {
					repairOrder.setCategoryId(Integer.parseInt(categoryId));
				}
			}
			repairOrder.setUpdateTime(new Date());
			int updateRows = repairOrderManager.update(repairOrder);
			res.addRespose("updateRows", updateRows);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	public Res getMyRepairOrderList(User user) {
		Res res = new Res();
		try {
			List<RepairOrder> repairOrderList = null;
			repairOrderList = repairOrderManager.getRepairOrderList(user.getUsrId());
			res.addRespose("repairOrderList", repairOrderList);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}
	
	public Res getMyTaskOrder(User user) {
		Res res = new Res();
		try {
			List<RepairOrder> repairOrderList = null;
			repairOrderList = repairOrderManager.getMyTaskOrder(user.getUsrId());
			res.addRespose("repairOrderList", repairOrderList);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	@Transactional
	public Res completeOrder(String orderId, Long usrId) {
		Res res = new Res();
		try {
			if(ValidateUtils.isNull(orderId)){
				throw new ServiceException("订单id不能为空");
			}
			RepairOrder repairOrder = repairOrderManager.getByKey(Long.parseLong(orderId));
			if(repairOrder.getStatus()!=2){
				throw new ServiceException("订单不能完成");
			}
			repairOrder.setStatus(3);
			repairOrder.setOptUserId(usrId);
			repairOrderManager.update(repairOrder);
		
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}
	
	@Transactional
	public Res commentOrder(String orderId, String star, String comment, User user) {
		Res res = new Res();
		try {
			
			if(ValidateUtils.isNull(orderId)){
				throw new ServiceException("订单id不能为空");
			}
			RepairOrder repairOrder = repairOrderManager.getByKey(Long.parseLong(orderId));
			if(user.getUsrId().longValue()!=repairOrder.getSubmitUserId().longValue()){
				throw new ServiceException("非本人订单不能评论");
			}
			if(repairOrder.getStatus()!=3){
				throw new ServiceException("未完成订单不能评论");
			}
			if(!ValidateUtils.isNull(comment)){
				repairOrder.setComment(comment);
			}
			if(ValidateUtils.isFloat(star)){
				repairOrder.setStar(Float.parseFloat(star));
			}
			repairOrder.setStatus(4);
			repairOrder.setOptUserId(user.getUsrId());
			repairOrderManager.update(repairOrder);
		
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	public Res getOrder(String orderId) {
		Res res = new Res();
		try {
			
			if(ValidateUtils.isNull(orderId)){
				throw new ServiceException("订单id不能为空");
			}
			RepairOrder repairOrder = repairOrderManager.getByKey(Long.parseLong(orderId));
			res.addRespose("repairOrder", repairOrder);
		
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

}
