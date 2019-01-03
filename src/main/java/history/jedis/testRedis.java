package history.jedis;

import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Created by  qiao
 * @date 18-4-9 下午6:30
 */

public class testRedis {
    private static Map<String, String> configs = new ConcurrentHashMap();
    private static final String SERVERPATH = "/home/qiao/IdeaProjects/tanklegendnetty/src/main/resources/tanklegendNettyServer.properties";
    private static final String REDISPATH = "/home/qiao/IdeaProjects/tanklegendnetty/src/main/resources/jedis.properties";

    public static void main(String[] args) {
        BufferedReader serverIn = null;
        String conf = "";
        try {
            serverIn = new BufferedReader(new FileReader(REDISPATH));
            while ((conf = serverIn.readLine()) != null) {
                String[] pair = conf.split("=");
                if (pair.length != 2) {
                    throw new Exception("Server config error");
                } else {
                    /*0--->key  1--->value*/
                    configs.put(pair[0], pair[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(configs.get("jp.host"));


        BlockingQueue<String> workpool = new ArrayBlockingQueue<String>(20);
        String  aa =workpool.poll();
        System.out.println(aa);

    }
}
