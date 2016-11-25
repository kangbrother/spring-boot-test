package com.lk.service.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.lk.dao.MessagesDao;
import com.lk.pojo.Messages;
import com.lk.service.TestService;
import com.lk.util.ChangeDatabase;
import com.lk.util.OResultVO;
import com.lk.util.RedisAPI;

@Service
public class TestServiceImpl implements TestService {
	
	private final Logger logger = Logger.getLogger(TestServiceImpl.class);
	
	@Autowired
	private MessagesDao messagesDao;
	
	@Override
	public OResultVO getTest(Integer city_id) {
		String key = "getTest"+"-"+city_id;
		JedisPool pool = null;
	    Jedis jedis = null;
		try {
			pool = RedisAPI.getPool();
			jedis = RedisAPI.getResource(pool);
			if(jedis==null){
				logger.info("jedis 对象为空，从数据库中去数据");
				String user =  messagesDao.selectUser();
				ChangeDatabase.valueOf(city_id);
				String house = messagesDao.selectHouse();
				return new OResultVO(true, 200, house+"-"+user);
			}else{
				String result = jedis.get(key);
				if(result==null){
					logger.info("key："+key+" 在缓存中不存，从数据库中取数据");
					String user =  messagesDao.selectUser();
					ChangeDatabase.valueOf(city_id);
					String house = messagesDao.selectHouse();
					result = house+"-"+user;
					//把数据保存到缓存
					logger.info("key："+key+" 把数据保存到缓存");
					jedis.set(key, result);
					//把数据返回前端
					return new OResultVO(true, 200, result);
				}else{
					logger.info("从缓存中获取数据");
					return new OResultVO(true, 200, result);
				}
			}
		} catch (Exception e) {
			logger.error("getTest 服务器异常",e);
			return new OResultVO(true, 500, "服务器异常");
		}finally{
			//返还到连接池
			logger.info("返还到连接池");
			RedisAPI.returnResource(pool, jedis);
		}
	
	}
	
	@Override
	public String getTest1(Integer city_id) {
		String user =  messagesDao.selectUser();
		return user;
	}

	@Override
	public String getTest2(Messages message) {
		// TODO Auto-generated method stub
		return "tests -- message";
	}

	@Override
	public OResultVO getMapData(Integer loc_type) {
		try {
			List<Map<String,Object>> points = messagesDao.selectpointList(loc_type);
			List<Map<String,Object>> maplist = messagesDao.selectMapData();
			Map<String,List<Map<String,Object>>> mapData = new HashMap<String, List<Map<String,Object>>>();
			for (Map<String, Object> map : maplist) {
				String plate = map.get("plate").toString();
				if(mapData.get(plate)==null){
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					list.add(map);
					mapData.put(plate, list);
				}else{
					mapData.get(plate).add(map);
				}
			}
			Map<String, Object> result=new HashMap<String, Object>();
			result.put("points", points);
			result.put("mapData", mapData);
			return new OResultVO(true, 200, result);
		} catch (Exception e) {
			e.printStackTrace();
			return new OResultVO(false, 500, "服务器错误");
		}
	}

}
