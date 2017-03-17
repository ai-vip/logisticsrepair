package com.logistics.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logistics.persistence.manager.UserInfoManager;
import com.logistics.persistence.model.UserInfo;

import com.logistics.common.utils.ValidateUtils;
import com.logistics.common.utils.exception.ServiceException;
import com.logistics.common.utils.response.Res;

@Service
public class UserInfoService {
	@Autowired
	UserInfoManager userInfoManager;


	public Res getUserInfo(Long userId) {
		Res res = new Res();
		try {
			UserInfo userInfo = userInfoManager.getUserInfo(userId);
			res.addRespose("userInfo", userInfo);;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	@Transactional
	public Res updateUserInfo(Long userId, String trueName, String phone, String address) {
		Res res = new Res();
		try {
			boolean isNew=false;
			UserInfo userInfo = userInfoManager.getUserInfo(userId);
			if(userInfo==null){
				userInfo=new UserInfo();
				isNew=true;
			}
			if(!ValidateUtils.isNull(trueName)){
				userInfo.setTrueName(trueName);
			}
			if(!ValidateUtils.isNull(phone)){
				userInfo.setPhone(phone);
			}
			if(!ValidateUtils.isNull(address)){
				userInfo.setAddress(address);
				
			}
			userInfo.setUpdateTime(new Date());
			if(isNew){
				userInfo.setUsrId(userId);
				userInfo.setCreateTime(new Date());
				userInfoManager.insert(userInfo);
			}else{
				userInfoManager.update(userInfo);
			}
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

}
