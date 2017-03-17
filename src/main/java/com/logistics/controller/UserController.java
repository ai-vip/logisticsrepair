package com.logistics.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.logistics.persistence.model.User;
import com.logistics.service.UserService;

import com.logistics.common.utils.exception.ServiceException;
import com.logistics.common.utils.response.Res;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 用户登录接口
	 * 
	 * @param userName
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "rememberMe", required = false) String rememberMe,
			@RequestParam(value = "directPage", required = false) String directPage, 
			HttpServletRequest request,HttpServletResponse response) {
		Res res = new Res();
		try {
			res = userService.login(userName, password, rememberMe, request, response);
		} catch (ServiceException e) {
		    e.printStackTrace();
		    res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
	/**
	 * 判断是否登录
	 * @param directPage
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkLogin")
	public ModelAndView isLogin(
			@RequestParam(value = "directPage", required = false) String directPage, 
			HttpServletRequest request,HttpServletResponse response) {
		Res res = new Res();
		try {
			User user = userService.getUser(request, response);
			if(user==null){
				throw new ServiceException("未登录");
			}
			res.addRespose("isLogin", 1);
			res.addRespose("groupId", user.getGroupId());
		} catch (ServiceException e) {
		    e.printStackTrace();
		    res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
	@ResponseBody
	@RequestMapping("/logout")
	public ModelAndView logout(
			@RequestParam(value = "directPage", required = false) String directPage, 
			HttpServletRequest request,HttpServletResponse response) {
		Res res = new Res();
		try {
			
			res = userService.logout(request, response);
		} catch (ServiceException e) {
		    e.printStackTrace();
		    res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
	/**
	 * 用户注册接口
	 * @param userName
	 * @param password
	 * @param password2
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/singIn")
	public ModelAndView singIn(
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "password2", required = false) String password2,
			HttpServletRequest request,HttpServletResponse response) {
		Res res = new Res();
		try {
			res = userService.singIn(userName,password,password2, request, response);
		} catch (ServiceException e) {
		    e.printStackTrace();
		    res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
	
	/**
	 * 修改密码
	 * @param password
	 * @param password2
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changePassword")
	public ModelAndView changePassword(
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "password2", required = false) String password2,
			HttpServletRequest request,HttpServletResponse response) {
		Res res = new Res();
		try {
			Long userId = userService.getUser(request, response).getUsrId();
			res = userService.changePassword(userId,password,password2);
		} catch (ServiceException e) {
		    e.printStackTrace();
		    res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
	/**
	 * 获取维修人员列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRepairManList")
	public ModelAndView getRepairManList(
			HttpServletRequest request,HttpServletResponse response) {
		Res res = new Res();
		try {
			res = userService.getRepairManList();
		} catch (ServiceException e) {
		    e.printStackTrace();
		    res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllUserList")
	public ModelAndView getAllUserList(
			HttpServletRequest request,HttpServletResponse response) {
		Res res = new Res();
		try {
			res = userService.getAllUserList();
		} catch (ServiceException e) {
		    e.printStackTrace();
		    res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
	
	@ResponseBody
	@RequestMapping("/addUser")
	public ModelAndView addUser(
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "usrMobile", required = false) String usrMobile,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "groupId", required = false) String groupId,
			HttpServletRequest request,HttpServletResponse response) {
		Res res = new Res();
		try {
			res = userService.addUser(userName,password, usrMobile, groupId,address);
		} catch (ServiceException e) {
		    e.printStackTrace();
		    res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
	
	@ResponseBody
	@RequestMapping("/removeUser")
	public ModelAndView removeUser(
			@RequestParam(value = "userId", required = false) String userId,
			HttpServletRequest request,HttpServletResponse response) {
		Res res = new Res();
		try {
			res = userService.removeUser(userId);
		} catch (ServiceException e) {
		    e.printStackTrace();
		    res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
}
