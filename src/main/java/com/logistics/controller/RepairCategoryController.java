package com.logistics.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.logistics.service.RepairCategoryService;

import com.logistics.common.utils.exception.ServiceException;
import com.logistics.common.utils.response.Res;

@Controller
@RequestMapping("/repairCategory")
public class RepairCategoryController {
	@Autowired
	private RepairCategoryService repairCategoryService;

	/**
	 * 新增分类
	 * 
	 * @param name
	 * @param des
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addRepairCategory")
	public ModelAndView addRepairCategory(
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "des", required = false, defaultValue = "") String des, HttpServletRequest request,
			HttpServletResponse response) {
		Res res = new Res();
		try {
			res = repairCategoryService.addRepairCategory(name, des);
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
	@ResponseBody
	@RequestMapping("/removeRepairCategory")
	public ModelAndView removeRepairCategory(
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			res = repairCategoryService.removeRepairCategory(name);
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}

	/**
	 * 修改分类
	 * 
	 * @param id
	 * @param name
	 * @param des
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateRepairCategory")
	public ModelAndView updateRepairCategory(@RequestParam(value = "id", required = false, defaultValue = "") String id,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "des", required = false, defaultValue = "") String des, HttpServletRequest request,
			HttpServletResponse response) {
		Res res = new Res();
		try {
			res = repairCategoryService.updateRepairCategory(id, name, des);
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}

	@ResponseBody
	@RequestMapping("/deleteRepairCategory")
	public ModelAndView deleteRepairCategory(@RequestParam(value = "id", required = false, defaultValue = "") String id,
			HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			res = repairCategoryService.deleteRepairCategory(id);
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}

	/**
	 * 获取分类列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getRepairCategoryList")
	public ModelAndView getRepairCategoryList(HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			res = repairCategoryService.getRepairCategoryList();
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
}
