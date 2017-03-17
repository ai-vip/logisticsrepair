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
import com.logistics.service.RepairAllocationService;
import com.logistics.service.UserService;

import com.logistics.common.utils.exception.ServiceException;
import com.logistics.common.utils.response.Res;

@Controller
@RequestMapping("/repairAllocation")
public class RepairAllocationController {
	@Autowired
	private RepairAllocationService repairAllocationService;
	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping("/allotRepairOrder")
	public ModelAndView allotRepairOrder(
			@RequestParam(value = "orderId", required = false, defaultValue = "") String orderId,
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			HttpServletRequest request, HttpServletResponse response) {
		Res res = new Res();
		try {
			User user = userService.getUser(request, response);
			res = repairAllocationService.allotRepairOrder(orderId, userId,user.getUsrId());
		} catch (ServiceException e) {
			e.printStackTrace();
			res.addError(Res.MESSAGE_NAME_ERROR, e.getMessage());
		}
		return res.toView(response);
	}
}
