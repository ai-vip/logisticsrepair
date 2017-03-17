package com.logistics.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.logistics.service.IndexService;

import com.logistics.common.utils.exception.ServiceException;
import com.logistics.common.utils.response.Res;

@Controller
@RequestMapping("/index")
public class IndexController {
	@Autowired
	private IndexService indexService;

	@ResponseBody
	@RequestMapping("/getIndexInfo")
	public ModelAndView addPlacard(@RequestParam(value = "title", required = false, defaultValue = "") String title,
			@RequestParam(value = "status", required = false, defaultValue = "") String status,
			@RequestParam(value = "timeRange", required = false, defaultValue = "") String timeRange,
			@RequestParam(value = "pageNo", required = false, defaultValue = "") Integer pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "") Integer pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			res = indexService.getIndexInfo();
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
}
