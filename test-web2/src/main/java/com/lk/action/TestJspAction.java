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
		model.put("name", "张三");  
		return "welcome"; //自动寻找resources/templates中名字为welcome.vm的文件作为模板，拼装后返
	}
	
	@RequestMapping("/test")
	public String test() {
		return "test";
	}
}
