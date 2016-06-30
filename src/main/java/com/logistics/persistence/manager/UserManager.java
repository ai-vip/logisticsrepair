package com.logistics.persistence.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logistics.persistence.dao.UserDao;
import com.logistics.persistence.model.User;
import com.logistics.persistence.model.UserExample;

@Component
public class UserManager extends BaseManager<UserDao> {
	@Autowired
	private UserDao userDao;

	@Override
	public UserDao getDao() {
		return userDao;
	}

	public User getUser(String userName, String password) {
		UserExample example = new UserExample();
		example.createCriteria().andUsrAccountEqualTo(userName).andUsrPasswordEqualTo(password);
		List<User> list = userDao.getMapper().selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}

	public int update(User user) {
		return userDao.getMapper().updateByPrimaryKey(user);
	}

	public int insert(User user) {
		return userDao.getMapper().insert(user);
	}

	public User getBySessionKey(String sessionKey) {
		UserExample example = new UserExample();
		example.createCriteria().andSessionKeyEqualTo(sessionKey);
		List<User> list = userDao.getMapper().selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public User getUser(Long userId) {
		return userDao.getMapper().selectByPrimaryKey(userId);
	}

	public User getRepairMan(Long userId) {
		UserExample example = new UserExample();
		example.createCriteria().andUsrIdEqualTo(userId).andGroupIdEqualTo(2);
		List<User> list = userDao.getMapper().selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public User getUser(String userName) {
		UserExample example = new UserExample();
		example.createCriteria().andUsrAccountEqualTo(userName);
		List<User> list = userDao.getMapper().selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}

	public List<User> getRepairManList() {
		UserExample example = new UserExample();
		example.createCriteria().andGroupIdEqualTo(2);
		return userDao.getMapper().selectByExample(example);
	}

	public List<User> getAllUserList() {
		UserExample example = new UserExample();
		
		return userDao.getMapper().selectByExample(example);
	}

	public int removeUser(Long usrId) {
		return userDao.getMapper().deleteByPrimaryKey(usrId);
	}
}
