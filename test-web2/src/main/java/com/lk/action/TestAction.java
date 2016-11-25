package com.lk.action;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestAction {
	
	@RequestMapping("/getUserName")
	public String getuser() {
		return "lukang";
	}
	
}
