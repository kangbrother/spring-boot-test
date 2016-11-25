package com.lk.service;

import com.lk.pojo.Messages;
import com.lk.util.OResultVO;


public interface TestService {
	
		OResultVO getTest(Integer city_id);
		
		String getTest1(Integer city_id);
		
		String getTest2(Messages message);
		
		OResultVO getMapData(Integer loc_type);
}
