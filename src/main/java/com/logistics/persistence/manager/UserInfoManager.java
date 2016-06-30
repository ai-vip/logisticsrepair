package com.logistics.persistence.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.logistics.persistence.dao.UserInfoDao;
import com.logistics.persistence.model.UserInfo;
import com.logistics.persistence.model.UserInfoExample;
@Component
public class UserInfoManager extends BaseManager<UserInfoDao> {
	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public UserInfoDao getDao() {
		return userInfoDao;
	}

	public UserInfo getUserInfo(Long userId) {
		UserInfoExample example = new UserInfoExample();
		example.createCriteria().andUsrIdEqualTo(userId);
		List<UserInfo> list = userInfoDao.getMapper().selectByExample(example);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public int update(UserInfo userInfo) {
		return userInfoDao.getMapper().updateByPrimaryKey(userInfo);
	}

	public int insert(UserInfo userInfo) {
		return userInfoDao.getMapper().insert(userInfo);
	}
}
