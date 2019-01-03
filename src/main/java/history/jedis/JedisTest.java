package history.jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Created by  qiao
 * 
 * @date 18-3-26 下午9:32
 */

public class JedisTest {
    public static void main(String[] args) {
        /*创建一个连接 参数为 host port*/
        Jedis jedis =new  Jedis("192.168.178.1",6379);
        /*直接使用Jedis操作redis*/
        jedis.set("test123", "myfiest jedis test ");
        String string = jedis.get("test123");
        System.out.println(string);
        jedis.close();
    }

    @Test
    public  void testJedisPool() throws Exception{
        /*创建连接池对象 -h -p*/
        JedisPool jedisPool = new JedisPool("192.168.178.1", 6379);
        /*从连接池火气一个连接*/
        Jedis jedis=jedisPool.getResource();
        /*使用jede*/
        String string = jedis.get("test123");
        System.out.println(string);
        jedis.close();
        jedisPool.close();
    }

//    @Test
//    public void testJedisCluster() {
//        /*创建一个JedisCluster对象,有一个参数nodes是一个set类型,set中包含若干个HostAndPort*/
//        JedisCluster jedisdisCluster=new JedisCluster();
//        /*直接使用JedisCluster*/
//    }

}


