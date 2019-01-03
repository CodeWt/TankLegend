package service;

import com.alibaba.fastjson.JSONObject;
import dao.JsonKeyword;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import utils.redis.TankJedisPool;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Created by  qiao
 * @date 18-4-11 下午11:49
 */

public class RegisterService {
    private static Logger logger = Logger.getLogger(RegisterService.class.getName());
    private ReentrantLock lock = new ReentrantLock();
    private TankJedisPool tankJedisPool;
    private static int i = 0;

    public RegisterService(TankJedisPool tankJedisPool) {
        this.tankJedisPool = tankJedisPool;
    }

    public void setTankJedisPool(TankJedisPool tankJedisPool) {
        this.tankJedisPool = tankJedisPool;
        System.out.println("设置成功,已退出.");
    }

    public String channelRead(JSONObject jsonObject) throws Exception {
//        JSONObject jsonObject = (JSONObject) msg;
        if (jsonObject.get(JsonKeyword.TYPE).equals(JsonKeyword.REGIST)) {
            String username = (String) jsonObject.get(JsonKeyword.USERNAME);
            String passwd = (String) jsonObject.get(JsonKeyword.PASSWORD);
            String checkcode = (String) jsonObject.get(JsonKeyword.CHECKCODE);
            String state = jedisCheckUser(username, passwd, checkcode);
            logger.info(username+"后端处理完得到的值===>RegisterHandler:channelRead"+state);
            return state;

//            ctx.writeAndFlush(state);
//            if (state.equals("=")) {
//                String sql = "insert into userinfo values(?,?)";
////                mysqlAdd(sql, username, passwd);
//            }
        } else {
            return "============> Register ERROR !! <============";
        }
    }

    private String jedisCheckUser(String username, String passwd, String checkcode) {

        Jedis jedis = null;
        int count = 0;
        jedis = tankJedisPool.getConnection();
        lable:
        while (true) {
            try {
                lock.lock();
                if (!jedis.hexists(JsonKeyword.USERS, username)) {
                    if(jedis.exists(username)){
                        jedis.del(username);
                    }
                    jedis.hset(JsonKeyword.USERS, JsonKeyword.USERNAME, username);
                    jedis.hset(JsonKeyword.USERS,JsonKeyword.PASSWORD,passwd);
                    jedis.hset(JsonKeyword.USERS,JsonKeyword.CHECKCODE, checkcode);
                    tankJedisPool.putbackConnection(jedis);

                    logger.info(username + "业务处理完返回值===>jedisCheckUser 2");
                    return "2";
                } else {
                    logger.warn("信息插入jedis错误!!");

//                    String code = jedis.get(username);
//                    if (code == null) {
//                        if(jedis.exists(username)){
//                            jedis.del(username);
//                        }
//                        tankJedisPool.putbackConnection(jedis);
//                        logger.info(username+"业务处理完返回值===>jedisCheckUser 0");
//                        return "0";
//                    } else if (code.equals(checkcode)) {
//                        jedis.hset(JsonKeyword.USERS, username, passwd);
//                        if(jedis.exists(username)){
//                            jedis.del(username);
//                        }
//                        tankJedisPool.putbackConnection(jedis);
//                        logger.info(username+"业务处理完返回值===>jedisCheckUser 1");
//                        return "1";
//                    } else {
//                        logger.info(username+"业务处理完返回值===>jedisCheckUser 3");
//                        return "3";
//                    }
                }
            } catch (Exception e) {
                logger.warn(e +username+ "===>jedisLpush");
                if (count++ >= 2) {
                    if(jedis.exists(username)){
                        jedis.del(username);
                    }
                    logger.error(username+"暂时不能访问redis===>jedisLpush 4");
                    return "4";
                } else {
                    tankJedisPool.repairConnection(jedis);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e1) {
                        logger.error(e1 + "thread is error in jedisLpush");
                    }
                    continue;
                }
            } finally {
                lock.unlock();
            }
        }
    }

//    private int mysqlAdd(String sql, String username, String passwd) {
//        int a = 0;
//        int count = 0;
//        while (true) {
//            try {
//                a = ConnPoolUtil.updata(sql, username, passwd);
//                logger.info(username+"通过mysqlAdd访问数据库返回结果"+a);
//                break;
//            } catch (Exception e) {
//                logger.warn(e +username+ "===>msqlAdd");
//                if (count++ >= 2) {
//                    logger.error(username+"暂时不能访问数据库===>mysqlAdd "+a);
//                    return a;
//                }
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e1) {
//                    logger.error(e1 + "thread sleep is error in mysqlAdd");
//                }
//                continue;
//            }
//        }
//        return a;
//    }
}
