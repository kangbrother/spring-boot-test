package com.lk.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;



public interface MessagesDao{
	
	String selectUser();
	
	String selectHouse();  
	
	Map<String,Object> selectMap_Max_Min_point();
	
	List<Map<String,Object>> selectMapData();
	
	List<Map<String,Object>> selectpointList(@Param("loc_type") Integer loc_type);
	
}