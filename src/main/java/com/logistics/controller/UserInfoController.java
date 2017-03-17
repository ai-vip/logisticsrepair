package com.logistics.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.logistics.service.UserInfoService;
import com.logistics.service.UserService;

import com.logistics.common.utils.exception.ServiceException;
import com.logistics.common.utils.response.Res;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 获取个人信息
	 * @param directPage
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserInfo")
	public ModelAndView getUserInfo(@RequestParam(value = "directPage", required = false) String directPage,
			HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			Long userId = userService.getUser(request, response).getUsrId();
			res=userInfoService.getUserInfo(userId);
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
	/**
	 * 修改个人信息
	 * @param trueName
	 * @param phone
	 * @param address
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateUserInfo")
	public ModelAndView updateUserInfo(
			@RequestParam(value = "trueName", required = false) String trueName,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "address", required = false) String address,
			HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			Long userId = userService.getUser(request, response).getUsrId();
			res=userInfoService.updateUserInfo(userId,trueName,phone,address);
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
}
