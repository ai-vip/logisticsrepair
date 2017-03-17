package com.logistics.common.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * 封装返回json数据
 * @author huhaichao
 *
 */
public class Res {
	public static final  String MESSAGE_NAME_ERROR="error";
	//public static final  String MESSAGE_NAME_EXCEPTION="exception";
	
	private int code;
	private Map<String, Object> response;
	private Map<String, Object> message;

	public Res() {
		code = 200;
		response = new HashMap<String, Object>();
		message=new HashMap<String, Object>();
	}
	
	public ModelAndView toView() {
		ModelAndView mav = new ModelAndView();
		
		MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
		Map<String, Object> attributes = new HashMap<String, Object>();
		if (code == 200) {
			attributes.put("code", code);
			attributes.put("response", response);
		} else {
			attributes.put("code", code);
			attributes.put("message", message);
		}
		jsonView.setAttributesMap(attributes);
		
		mav.setView(jsonView);
		return mav;

	}
	
	//生成getter和setter，方便直接返回成其他格式时使用
	public void addRespose(String key, Object data) {
		response.put(key, data);
	}

	public void addError(String key, Object data) {
		code = 500;
		message.put(key, data);
	}
	public void addNoPermission(String key, Object data) {
		code = 600;
		message.put(key, data);
	}

	public Map<String, Object> getResponse() {
		return response;
	}

	public void setResponse(Map<String, Object> response) {
		this.response = response;
	}

	public Map<String, Object> getMessage() {
		return message;
	}

	public void setMessage(Map<String, Object> message) {
		this.message = message;
	}

	
}
