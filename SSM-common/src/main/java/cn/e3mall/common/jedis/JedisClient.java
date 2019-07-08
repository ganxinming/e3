package cn.e3mall.common.jedis;

import java.util.List;

/**
 * 提供简便的redis常用操作，接口
 * set:添加一个缓存，get：取得一个缓存
 * hset：放置到指定hash域中，作为缓存(所以有三个参数)
 */
public interface JedisClient {

	String set(String key, String value);
	String get(String key);
	Boolean exists(String key);
	Long expire(String key, int seconds);
	Long ttl(String key);
	Long incr(String key);
	Long hset(String key, String field, String value);
	String hget(String key, String field);
	Long hdel(String key, String... field);
	Boolean hexists(String key, String field);
	List<String> hvals(String key);
	Long del(String key);
}
