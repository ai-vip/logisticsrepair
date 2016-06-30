package com.logistics.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logistics.persistence.manager.UserInfoManager;
import com.logistics.persistence.manager.UserManager;
import com.logistics.persistence.model.User;
import com.logistics.persistence.model.UserInfo;

import common.utils.ValidateUtils;
import common.utils.exception.ServiceException;
import common.utils.login.SessionBean;
import common.utils.response.Res;

@Service
public class UserService {
	@Autowired
	UserManager userManager;
	@Autowired
	UserInfoManager userInfoManager;

	/**
	 * 登录
	 * 
	 * @param userName
	 * @param password
	 * @param rememberMe
	 * @param request
	 * @param response
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Res login(String userName, String password, String rememberMe, HttpServletRequest request,
			HttpServletResponse response) {
		Res res = new Res();
		try {
			if (ValidateUtils.isNull(userName)) {
				throw new SerialException("用户名不能为空");
			}
			if (ValidateUtils.isNull(password)) {
				throw new SerialException("密码不能为空");
			}

			User user = userManager.getUser(userName, password);
			if (user == null) {
				request.getSession().setAttribute("sessionBean", null);
				throw new ServiceException("用户名或者密码错误");
			} else {
				user.setIsOnline(1);
				user.setLoginIp(request.getRemoteAddr());
				Integer loginTimes = user.getLoginTimes();
				if (loginTimes == null) {
					loginTimes = 0;
				}
				user.setLoginTimes(loginTimes + 1);
				user.setOnlineTime(new Date());
				user.setLastLoginTime(new Date());
				user.setLoginUserAgent(request.getHeader("User-Agent"));

				user.setSessionKey(request.getSession().getId() + "_" + user.getUsrId());

				userManager.update(user);

				SessionBean sessionBean = new SessionBean();
				sessionBean.setGroupId(user.getGroupId());
				sessionBean.setMobile(user.getUsrMobile());
				sessionBean.setNickName(user.getUsrNickName());
				sessionBean.setSessionKey(user.getSessionKey());
				sessionBean.setUserAccount(user.getUsrAccount());
				sessionBean.setUserId(user.getUsrId());
				request.getSession().setAttribute("sessionBean", sessionBean);
				if (ValidateUtils.isInt(rememberMe) && Integer.parseInt(rememberMe) == 1) {
					Cookie cookie = new Cookie("sessionKey", user.getSessionKey());
					cookie.setMaxAge(7 * 24 * 60 * 60);
					cookie.setPath("/");
					cookie.setDomain(".tulingcloud.cn");
					response.addCookie(cookie);
				} else {
					Cookie cookie = new Cookie("sessionKey", user.getSessionKey());
					cookie.setMaxAge(10 * 60);
					cookie.setPath("/");
					cookie.setDomain(".tulingcloud.cn");
					response.addCookie(cookie);
				}

				res.addRespose("user", user);
			}
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	@Transactional
	public Res singIn(String userName, String password, String password2, HttpServletRequest request,
			HttpServletResponse response) {

		Res res = new Res();
		try {
			if (ValidateUtils.isNull(userName)) {
				throw new ServiceException("用户名不能为空");
			}
			if (ValidateUtils.isNull(password) || ValidateUtils.isNull(password2)) {
				throw new ServiceException("密码不能为空");
			}
			if (userName.length() < 3) {
				throw new ServiceException("用户名不能小于3个字符");
			}
			if (password.length() < 6) {
				throw new ServiceException("密码不能小于6个字符");
			}

			if (!password.equals(password2)) {
				throw new ServiceException("密码和确认密码不相同");
			}

			User temp = userManager.getUser(userName);
			if (temp != null) {
				throw new SerialException("用户名已被占用");
			}

			User user = new User();
			user.setUsrAccount(userName);

			user.setUsrNickName(userName);
			user.setUsrPassword(password);
			user.setGroupId(1);
			user.setRegisterTime(new Date());
			user.setRegisterIp(request.getRemoteAddr());

			user.setRegUserAgent(request.getHeader("User-Agent"));
			user.setIsOnline(0);
			user.setStatus(1);
			userManager.insert(user);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;

	}

	// 获取登录用户id
	@Transactional
	public User getUser(HttpServletRequest request, HttpServletResponse response) {
		boolean isOnline = false;
		try {
			User user = null;

			String sessionKey = (String) request.getSession().getAttribute("sessionKey");
			if (sessionKey != null) {
				user = userManager.getBySessionKey(sessionKey);
				if (user != null && user.getIsOnline() == 1) {
					isOnline = true;
				}
			}

			if (isOnline) {
				return user;
			} else {
				throw new ServiceException("未登录");
			}
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Transactional
	public Res changePassword(Long userId, String password, String password2) {

		Res res = new Res();
		try {
			if (ValidateUtils.isNull(password) || ValidateUtils.isNull(password2)) {
				throw new ServiceException("密码不能为空");
			}
			User user = userManager.getUser(userId);
			if (!user.getUsrPassword().equals(password)) {
				throw new ServiceException("原密码错误");
			}
			if (password2.length() < 6) {
				throw new ServiceException("密码长度不能小于6位");
			}
			user.setUsrPassword(password2);
			userManager.update(user);

		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;

	}

	@Transactional
	public Res logout(HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			User user = getUser(request, response);
			user.setIsOnline(0);
			user.setSessionKey(null);
			request.getSession().removeAttribute("sessionKey");
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("sessionKey")) {
					cookie.setMaxAge(0);
				}
			}
			userManager.update(user);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	public Res getRepairManList() {
		Res res = new Res();
		try {
			List<User> repairManList = userManager.getRepairManList();
			res.addRespose("repairManList", repairManList);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	public Res getAllUserList() {
		Res res = new Res();
		try {
			List<User> userList = userManager.getAllUserList();
			res.addRespose("userList", userList);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	@Transactional
	public Res addUser(String userName, String password, String usrMobile, String groupId, String address) {
		Res res = new Res();
		try {
			User user = new User();
			if (ValidateUtils.isNull(userName)) {
				throw new ServiceException("用户名不能为空");
			} else if (userName.length() < 3) {
				throw new ServiceException("用户名长度不能小于3");
			}
			if (ValidateUtils.isNull(password)) {
				throw new ServiceException("密码长度不能小于6");
			} else if (password.length() < 6) {
				throw new ServiceException("密码长度不能小于6");
			}

			if (!ValidateUtils.isInt(groupId)) {
				throw new ServiceException("用户类型错误");
			} else {
				int gid = Integer.parseInt(groupId);
				if (gid != 1 && gid != 2 && gid != 3) {
					throw new ServiceException("分用户类型错误");
				}
			}
			//用户注册信息
			user.setUsrAccount(userName);
			user.setUsrPassword(password);
			user.setUsrMobile(usrMobile);
			user.setGroupId(Integer.parseInt(groupId));
			user.setRegisterTime(new Date());
			userManager.insert(user);
			//用户基本信息
			UserInfo userInfo = new UserInfo();
			userInfo.setUsrId(user.getUsrId());
			userInfo.setPhone(usrMobile);
			userInfo.setAddress(address);
			userInfo.setTrueName(userName);
			userInfo.setUpdateTime(new Date());
			userInfo.setCreateTime(new Date());
			userInfoManager.insert(userInfo);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

	public Res removeUser(String userId) {
		Res res = new Res();
		try {
		if(!ValidateUtils.isLong(userId)){
			throw new ServiceException("用户id错误");
		}
		if("1".equals(userId)){
			throw new ServiceException("不能删除系统管理员admin");
		}
		userManager.removeUser(Long.parseLong(userId));
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return res;
	}

}
