package test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisUtilTest {
	


	@Before
	public void setUp() {
		
		// jedis.auth("password");
	}

	@Test
	public void testGet() {
		JedisPool pool = null;
	    Jedis jedis = null;
		try {
			pool = RedisAPI.getPool();
			try {
				jedis = pool.getResource();
			} catch (Exception e) {
				 //释放redis对象
	            pool.returnBrokenResource(jedis);
				System.out.println("redis异常="+e.getMessage());
			}
			if(jedis==null){
				System.out.println("从数据库中取出数据");
				throw new Exception("运行时异常");
			}else{
				String str = jedis.get("lu");
				if(str==null){
					System.out.println("从数据库中取出数据");
					System.out.println("把数据保存到缓存中");
				}else{
					System.out.println(jedis.get("lu"));
				}
			}
		} catch (Exception e) {
			System.out.println("服务器异常");
		}finally{
			//返还到连接池
			RedisAPI.returnResource(pool, jedis);
		}
	}

	/**
	 * Redis�洢�������ַ� CRUD
	 */
	@Test
	public void testBasicString() {
		JedisPool pool = null;
	    Jedis jedis = null;
		try {
			pool = RedisAPI.getPool();
			jedis = pool.getResource();
			// -----������----------
			jedis.set("name", "minxr");// ��key-->name�з�����value-->minxr
			System.out.println(jedis.get("name"));// ִ�н��minxr
			// -----�޸����-----------
			// 1����ԭ�������޸�
			jedis.append("name", "jarorwar"); // ��ֱ�ۣ�����map ��jarorwar
												// append���Ѿ��е�value֮��
			System.out.println(jedis.get("name"));// ִ�н��:minxrjarorwar
			// 2��ֱ�Ӹ���ԭ�������
			jedis.set("name", "������");
			System.out.println(jedis.get("name"));// ִ�н��������
			// ɾ��key��Ӧ�ļ�¼
			jedis.del("name");
			System.out.println(jedis.get("name"));// ִ�н��null
			/**
			 * mset�൱�� jedis.set("name","minxr"); jedis.set("jarorwar","������");
			 */
			jedis.mset("name", "minxr", "jarorwar", "������");
			System.out.println(jedis.mget("name", "jarorwar"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pool.returnBrokenResource(jedis);
		}finally{
			//返还到连接池
			RedisAPI.returnResource(pool, jedis);
		}
	}

	/**
	 * jedis����Map
	 */
	@Test
	public void testMap() {
		JedisPool pool = null;
	    Jedis jedis = null;
		
		try {
			pool = RedisAPI.getPool();
			jedis = pool.getResource();
			Map<String, String> user = new HashMap<String, String>();
			user.put("name", "lukang");
			user.put("pwd", "123456");
			jedis.hmset("user", user);
			// ȡ��user�е�name��ִ�н��:[minxr]-->ע������һ�����͵�List
			// ��һ�������Ǵ���redis��map�����key���������Ƿ���map�еĶ����key�������key���Ը������ǿɱ����
			List<String> rsmap = jedis.hmget("user", "name");
			System.out.println(rsmap);
			// ɾ��map�е�ĳ����ֵ
			// jedis.hdel("user","pwd");
			System.out.println(jedis.hmget("user", "pwd")); // ��Ϊɾ���ˣ����Է��ص���null
			System.out.println(jedis.hlen("user")); // ����keyΪuser�ļ��д�ŵ�ֵ�ĸ���1
			System.out.println(jedis.exists("user"));// �Ƿ����keyΪuser�ļ�¼ ����true
			System.out.println(jedis.hkeys("user"));// ����map�����е�����key [pwd, name]
			System.out.println(jedis.hvals("user"));// ����map�����е�����value [minxr,
													// password]
			Iterator<String> iter = jedis.hkeys("user").iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				System.out.println(key + ":" + jedis.hmget("user", key));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pool.returnBrokenResource(jedis);
		}finally{
			//返还到连接池
			RedisAPI.returnResource(pool, jedis);
		}
	}

	/**
	 * jedis����List
	 */
	/*@Test
	public void testList() {
		// ��ʼǰ�����Ƴ����е�����
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));
		// ����key java framework�д���������
		jedis.lpush("java framework", "spring");
		jedis.lpush("java framework", "struts");
		jedis.lpush("java framework", "hibernate");
		// ��ȡ���������jedis.lrange�ǰ���Χȡ����
		// ��һ����key���ڶ�������ʼλ�ã�������ǽ���λ�ã�jedis.llen��ȡ���� -1��ʾȡ������
		System.out.println(jedis.lrange("java framework", 0, -1));
	}
*/
	/**
	 * jedis����Set
	 */
	/*@Test
	public void testSet() {
		// ���
		jedis.sadd("sname", "minxr");
		jedis.sadd("sname", "jarorwar");
		jedis.sadd("sname", "������");
		jedis.sadd("sanme", "noname");
		// �Ƴ�noname
		jedis.srem("sname", "noname");
		System.out.println(jedis.smembers("sname"));// ��ȡ���м����value
		System.out.println(jedis.sismember("sname", "minxr"));// �ж� minxr
																// �Ƿ���sname���ϵ�Ԫ��
		System.out.println(jedis.srandmember("sname"));
		System.out.println(jedis.scard("sname"));// ���ؼ��ϵ�Ԫ�ظ���
	}*/

	/*@Test
	public void test() throws InterruptedException {
		// keys�д���Ŀ�����ͨ���
		System.out.println(jedis.keys("*")); // ���ص�ǰ�������е�key [sose, sanme, name,
												// jarorwar, foo, sname, java
												// framework, user, braand]
		System.out.println(jedis.keys("*name"));// ���ص�sname [sname, name]
		System.out.println(jedis.del("sanmdde"));// ɾ��keyΪsanmdde�Ķ��� ɾ��ɹ�����1
													// ɾ��ʧ�ܣ����߲����ڣ����� 0
		System.out.println(jedis.ttl("sname"));// ���ظ�key����Чʱ�䣬�����-1���ʾ��Զ��Ч
		jedis.setex("timekey", 10, "min");// ͨ��˷���������ָ��key�Ĵ���Чʱ�䣩 ʱ��Ϊ��
		Thread.sleep(5000);// ˯��5���ʣ��ʱ�佫Ϊ<=5
		System.out.println(jedis.ttl("timekey")); // ������Ϊ5
		jedis.setex("timekey", 1, "min"); // ��Ϊ1�������ٿ�ʣ��ʱ�����1��
		System.out.println(jedis.ttl("timekey")); // ������Ϊ1
		System.out.println(jedis.exists("key"));// ���key�Ƿ����
												// System.out.println(jedis.rename("timekey","time"));
		System.out.println(jedis.get("timekey"));// ��Ϊ�Ƴ��Ϊnull
		System.out.println(jedis.get("time")); // ��Ϊ��timekey ������Ϊtime ���Կ���ȡ��ֵ
												// min
		// jedis ����
		// ע�⣬�˴���rpush��lpush��List�Ĳ�������һ��˫�����?���ӱ��������ģ�
		jedis.del("a");// �������ݣ��ټ�����ݽ��в���
		jedis.rpush("a", "1");
		jedis.lpush("a", "6");
		jedis.lpush("a", "3");
		jedis.lpush("a", "9");
		System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
		System.out.println(jedis.sort("a")); // [1, 3, 6, 9] //�����������
		System.out.println(jedis.lrange("a", 0, -1));
	}*/
	
	/*@Test
	public void testSetTime(){
		//jedis.setex("2", 10, "min");// ͨ��˷���������ָ��key�Ĵ���Чʱ�䣩 ʱ��Ϊ��
		System.out.println(jedis.ttl("sex"));// ���ظ�key����Чʱ�䣬�����-1���ʾ��Զ��Ч
	}*/
	
	/*@Test
	public void testPrintKey(){
//		System.out.println(jedis.keys("*"));
		Set<String> keys = jedis.keys("*");
//		for (String key : keys) {
//			jedis.del(key);
//		}
		
//		jedis.set("name", "lukang");
		System.out.println(jedis.keys("*"));
//		jedis.bgsave();
	}*/
}
