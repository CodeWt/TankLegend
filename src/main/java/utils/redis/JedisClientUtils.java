package utils.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @Created by  qiao
 * @date 18-3-27 下午4:50
 */

public class JedisClientUtils implements JedisClient {
    private JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }



    @Override
    public String set(String key, String vlaue) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.set(key, vlaue);
        jedis.close();
        return result;
    }

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get(key);
        return result;
    }

    @Override
    public Long expire(String key, int seconds) {
        Jedis jedis =jedisPool.getResource();
        Long result = jedis.expire(key,seconds);
        return result;
    }

    @Override
    public Long ttl(String key) {
        Jedis jedis =jedisPool.getResource();
        Long result = jedis.ttl(key);
        return result;
    }

    @Override
    public Long incr(String key) {
        Jedis jedis =jedisPool.getResource();
        Long result = jedis.incr(key);
        return result;
    }

    @Override
    public Long hset(String key, String field, String value) {
        Jedis jedis =jedisPool.getResource();
        Long result = jedis.hset(key, field, value);
        jedis.close();
        return result;
    }

    @Override
    public String hget(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.hget(key, field);
        jedis.close();
        return result;
    }

//    public boolean hListGet(JSONObject body) {
//
//    }

    @Override
    public Boolean exists(String key) {
        return null;
    }

    @Override
    public Long hdel(String key, String... field) {
        return null;
    }

    @Override
    public Boolean hexists(String key, String field) {
        return null;
    }

    @Override
    public List<String> hvals(String key) {
        return null;
    }

    @Override
    public Long del(String key) {
        return null;
    }



}
