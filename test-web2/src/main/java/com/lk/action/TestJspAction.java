package com.lk.action;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TestJspAction {
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/welcome")
	public String welcome(Map<String, Object> model) {
		model.put("name", "����");  
		return "welcome"; //�Զ�Ѱ��resources/templates������Ϊwelcome.vm���ļ���Ϊģ�壬ƴװ��
	}
	
	@RequestMapping("/test")
	public String test() {
		return "test";
	}
}
