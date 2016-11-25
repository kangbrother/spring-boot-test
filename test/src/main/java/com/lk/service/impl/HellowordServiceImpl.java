package com.lk.service.impl;

import org.springframework.stereotype.Service;

import com.lk.service.HellowordService;
@Service("hellowordService")
public class HellowordServiceImpl implements HellowordService {

	public String getHello() {
		return "hello word";
	}
	
}
