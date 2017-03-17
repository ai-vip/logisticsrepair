package com.logistics.persistence.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logistics.persistence.dao.RepairOrderDao;
import com.logistics.persistence.model.RepairOrder;
import com.logistics.persistence.model.RepairOrderExample;
import com.logistics.persistence.model.RepairOrderExample.Criteria;

import com.logistics.common.Page;
import com.logistics.common.utils.ValidateUtils;
@Component
public class RepairOrderManager extends BaseManager<RepairOrderDao> {
	@Autowired
	private RepairOrderDao repairOrderDao;

	@Override
	public RepairOrderDao getDao() {
		return repairOrderDao;
	}

	public List<RepairOrder> getRepairOrderList(Long userId) {
		RepairOrderExample example = new RepairOrderExample();
		if(userId!=null){
			example.createCriteria().andSubmitUserIdEqualTo(userId);
		}
		example.setOrderByClause("create_time desc");
		return repairOrderDao.getMapper().selectByExample(example);
	}
	public List<RepairOrder> queryRepairOrderList(String title,String status, String timeRange, Page page) {
		RepairOrderExample example = new RepairOrderExample();
		Criteria criteria = example.createCriteria();
		if(!ValidateUtils.isNull(title)){
			criteria.andTitleLike("%"+title+"%");
		}
		if(ValidateUtils.isInt(status)){
			criteria.andStatusEqualTo(Integer.parseInt(status));
		}
		if(ValidateUtils.isInt(timeRange)){
			if(Integer.parseInt(timeRange)==1){
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_MONTH,-7);
				Date time = calendar.getTime();
				criteria.andCreateTimeGreaterThan(time);
			}
			if(Integer.parseInt(timeRange)==2){
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH,-1);
				Date time = calendar.getTime();
				criteria.andCreateTimeGreaterThan(time);
			}
		}
		example.setOrderByClause("create_time desc");
		//分页
		page.setTotalCounts(repairOrderDao.getMapper().countByExample(example));
		example.setLimitStart(page.getPageSize()*page.getPageNo());
		example.setLimitEnd(page.getPageSize());
		
		return repairOrderDao.getMapper().selectByExample(example);
	}
	
	

	public int insert(RepairOrder repairOrder) {
		return repairOrderDao.getMapper().insert(repairOrder);
	}

	public RepairOrder getByKey(Long orderId) {
		return repairOrderDao.getMapper().selectByPrimaryKey(orderId);
	}

	public int update(RepairOrder repairOrder) {
		 return repairOrderDao.getMapper().updateByPrimaryKey(repairOrder);
	}

	public int getOrderCount(Integer status) {
		RepairOrderExample example = new RepairOrderExample();
		example.createCriteria().andStatusEqualTo(status);
		return repairOrderDao.getMapper().countByExample(example);
	}
	public int getOrderCount() {
		RepairOrderExample example = new RepairOrderExample();
		return repairOrderDao.getMapper().countByExample(example);
	}

	public List<RepairOrder> getMyTaskOrder(Long usrId) {
		RepairOrderExample example = new RepairOrderExample();
		if(usrId!=null){
			example.createCriteria().andRepairUserIdEqualTo(usrId);
		}
		example.setOrderByClause("create_time desc");
		return repairOrderDao.getMapper().selectByExample(example);
	}

}
