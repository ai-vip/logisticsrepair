package com.logistics.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.logistics.persistence.model.User;
import com.logistics.service.RepairOrderService;
import com.logistics.service.UserService;

import com.logistics.common.Page;
import com.logistics.common.utils.exception.ServiceException;
import com.logistics.common.utils.response.Res;

@Controller
@RequestMapping("/repairOrder")
public class RepairOrderController {
	@Autowired
	private RepairOrderService repairOrderService;
	@Autowired
	private UserService userService;

	/**
	 * 添加维修单
	 * 
	 * @param title
	 * @param des
	 * @param phone
	 * @param address
	 * @param categoryId
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrder")
	public ModelAndView addOrder(@RequestParam(value = "title", required = false, defaultValue = "") String title,
			@RequestParam(value = "des", required = false, defaultValue = "") String des,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "address", required = false, defaultValue = "") String address,
			@RequestParam(value = "categoryId", required = false, defaultValue = "") String categoryId,
			HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			User user = userService.getUser(request, response);
			res = repairOrderService.addOrder(title, des, phone, address, categoryId,user.getUsrId());
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}

	@ResponseBody
	@RequestMapping("/getOrder")
	public ModelAndView getOrder(
			@RequestParam(value = "orderId", required = false, defaultValue = "") String orderId,
			HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			
			res = repairOrderService.getOrder(orderId);
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
	
	//确认完成
	@ResponseBody
	@RequestMapping("/completeOrder")
	public ModelAndView completeOrder(
			@RequestParam(value = "orderId", required = false, defaultValue = "") String orderId,
			HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			User user = userService.getUser(request, response);
			res = repairOrderService.completeOrder(orderId,user.getUsrId());
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}


	/**
	 * 修改维修单
	 * 
	 * @param orderId
	 * @param title
	 * @param des
	 * @param phone
	 * @param address
	 * @param categoryId
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateOrder")
	public ModelAndView updateOrder(
			@RequestParam(value = "orderId", required = false, defaultValue = "") String orderId,
			@RequestParam(value = "title", required = false, defaultValue = "") String title,
			@RequestParam(value = "des", required = false, defaultValue = "") String des,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "address", required = false, defaultValue = "") String address,
			@RequestParam(value = "categoryId", required = false, defaultValue = "") String categoryId,
			HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			res = repairOrderService.updateOrder(orderId, title, des, phone, address, categoryId);
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}

	/**
	 * 查询所有维修单
	 * 
	 * @param name
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryRepairOrderList")
	public ModelAndView queryRepairOrderList(
			@RequestParam(value = "title", required = false, defaultValue = "") String title,
			@RequestParam(value = "status", required = false, defaultValue = "") String status,
			@RequestParam(value = "timeRange", required = false, defaultValue = "") String timeRange,
			@RequestParam(value = "pageNo", required = false, defaultValue = "") Integer pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "") Integer pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			Page page = new Page();
			page.setPageNo(pageNo == null ? 0 : pageNo);
			page.setPageSize(pageSize == null ? page.getPageSize() : pageSize);

			res = repairOrderService.queryRepairOrderList(title, status, timeRange, page);
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}

	/**
	 * 查询个人维修单
	 * 
	 * @param title
	 * @param status
	 * @param timeRange
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMyRepairOrderList")
	public ModelAndView getMyRepairOrderList(HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			User user = userService.getUser(request, response);
			res = repairOrderService.getMyRepairOrderList(user);
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
	/**
	 * 获取我维修的订单
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMyTaskOrder")
	public ModelAndView getMyTaskOrder(HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			User user = userService.getUser(request, response);
			res = repairOrderService.getMyTaskOrder(user);
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
	/**
	 * 评价订单
	 * @param orderId
	 * @param star
	 * @param comment
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/commentOrder")
	public ModelAndView commentOrder(
			@RequestParam(value = "orderId", required = false, defaultValue = "") String orderId,
			@RequestParam(value = "star", required = false, defaultValue = "") String star,
			@RequestParam(value = "comment", required = false, defaultValue = "") String comment,
			HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			User user = userService.getUser(request, response);
			res = repairOrderService.commentOrder(orderId,star,comment,user);
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}

	/**
	 * 测试接口
	 * 
	 * @param name
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/hello")
	public ModelAndView hello(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("name", name);
		try {
			if (name == null) {
				name = "";
			}
			response.getWriter().write("<h1>Hello " + name + "</h1>");
		} catch (IOException e) {
		}
		return null;
	}

}
