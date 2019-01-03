package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import dao.JsonKeyword;
import dao.UserTankCode;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import utils.redis.TankJedisPool;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Created by  qiao
 * @date 18-4-15 下午1:55
 */

public class FullHttpRequestUtils {
    private static Logger logger = Logger.getLogger(FullHttpRequestUtils.class.getName());
    private TankJedisPool tankJedisPool;

    public TankJedisPool getTankJedisPool() {
        return tankJedisPool;
    }

    private void setTankJedisPool(TankJedisPool tankJedisPool) {
        this.tankJedisPool = tankJedisPool;
    }

    public synchronized static JSONObject ContentToJson(Object msg) {
        FullHttpRequest request = (FullHttpRequest)msg;
        ByteBuf body = request.content();
        byte[] bytes = new byte[body.readableBytes()];
        JSONObject jsonObject =null;
        try {
            body.readBytes(bytes);
            String content = new String(bytes,"UTF-8");
            jsonObject = JSON.parseObject(content);
//            System.out.println(jsonObject.toJSONString()+"====>ContentToJson");
        } catch (UnsupportedEncodingException e) {
            logger.error("FullHttpRequestUtils.ContentToJson error!");
            e.printStackTrace();
        }
        return jsonObject;
    }

    public synchronized static JSONObject ContentToMap(Object msg) {
        FullHttpRequest request = (FullHttpRequest)msg;
        ByteBuf body = request.content();
        System.out.println(request.content());
        byte[] bytes = new byte[body.readableBytes()];
        Map<String, String> data = new HashMap<String, String>();
        JSONObject jsonObject = null;
        try {
            body.readBytes(bytes);
            String content = new String(bytes,"UTF-8");
            jsonObject = JSONObject.parseObject(content);
            System.out.println(jsonObject);

        } catch (Exception e) {
            logger.error("FullHttpRequestUtils.ContentToMap error!");
            e.printStackTrace();
        }
        return jsonObject;
    }



    public synchronized static String getUri(Object msg) {
        FullHttpRequest request = (FullHttpRequest)msg;
        String uri = request.uri().substring(1);
        return  uri;
    }

    public synchronized static boolean Sessionhandler(String username,String session, TankJedisPool tankJedisPool) {
        Jedis jedis = tankJedisPool.getConnection();
        String redisSession = null;
        redisSession=jedis.hget(JsonKeyword.SESSION, username);
        if (session.equals(redisSession)) {
            logger.info("get user session success.");
            return true;
        }else {
            logger.debug("get user session fail.");
            return false;
        }
    }

}
