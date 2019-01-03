package utils.redis;

import java.util.*;

/**
 * @Created by  qiao
 * @date 18-3-27 下午4:43
 */

public interface JedisClient {

    String set(String key, String vlaue);

    String get(String key);

    Long hset(String key, String field, String value);

    String hget(String key, String field);

    Boolean exists(String key);

    Long expire(String key, int seconds);

    Long ttl(String key);

    Long incr(String key);

    Long hdel(String key, String... field);

    Boolean hexists(String key, String field);

    List<String> hvals(String key);

    Long del(String key);





}



