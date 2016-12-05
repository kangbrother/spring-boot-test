package com.lk.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lk.service.TestService;
import com.lk.util.OResultVO;

@RestController
public class MapDataAction {
	
	@Autowired
	private TestService testService;
	
	
	@RequestMapping(value = "/test" ,method = RequestMethod.GET)
	@ResponseBody
	public OResultVO getTest(@RequestParam(required=false,value="cityId") Integer cityId,
			HttpServletRequest request, HttpServletResponse response){
		OResultVO result = testService.getTest(cityId);
		return result;
	}
	
	@RequestMapping(value = "/test1" ,method = RequestMethod.GET)
	@ResponseBody
	public OResultVO getTest1(@RequestParam(required=false,value="cityId") Integer cityId,
			HttpServletRequest request, HttpServletResponse response){
		String str = testService.getTest1(cityId);
		return new OResultVO(true, 200, str);
	}
	
	@RequestMapping(value = "/mapData" ,method = RequestMethod.GET)
	@ResponseBody
	public OResultVO getMap(
			@RequestParam(required=false,value="loc_type") Integer loc_type,
			HttpServletRequest request, HttpServletResponse response){
		return testService.getMapData(loc_type);
	}
	
	
}
