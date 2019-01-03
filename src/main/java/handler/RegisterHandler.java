package handler;

import com.alibaba.fastjson.JSONObject;
import dao.JsonKeyword;
import dao.UserLoginInfo;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import utils.C3P0Utils;
import utils.FullHttpRequestUtils;
import utils.redis.TankJedisPool;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class RegisterHandler extends ChannelHandlerAdapter {
    private static Logger logger = Logger.getLogger(RegisterHandler.class.getName());
    private TankJedisPool tankJedisPool;
    private UserLoginInfo userLoginInfo =new UserLoginInfo();
    private ReentrantLock lock = new ReentrantLock();

    public RegisterHandler(TankJedisPool tankJedisPool) {
        this.tankJedisPool = tankJedisPool;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String uri= FullHttpRequestUtils.getUri(msg);
        if (uri.equals("Register")) {
            JSONObject body=FullHttpRequestUtils.ContentToJson(msg);
            String username = body.getString(JsonKeyword.USERNAME);
            String passwd = body.getString(JsonKeyword.PASSWORD);

            userLoginInfo.setUname(body.getString(JsonKeyword.USERNAME));
            userLoginInfo.setUpassward(body.getString(JsonKeyword.PASSWORD));
            userLoginInfo.setUesr_login_game_info(body.getString(JsonKeyword.USER_LOGIN_GAME_INFO));
            userLoginInfo.setUser_login_tel(body.getString(JsonKeyword.USER_LOGIN_TEL));
            userLoginInfo.setUser_login_email(body.getString(JsonKeyword.USER_LOGIN_EMAIL));
            String StatusFlag = AddUserLoginInfoToRedis(userLoginInfo);

            ctx.writeAndFlush("["+username+"]: 注册"+StatusFlag).addListener(ChannelFutureListener.CLOSE);
            logger.info("["+username+"]: 注册情况("+StatusFlag+")!!");
            String sql = "insert into tbl_user values(null,?,?)";
            String sql1 = "insert into user_login_info values(null,?,?,?,?,?,?)";
            mysqlAdd(sql,username,passwd);
            mysqlAdd1(sql1, userLoginInfo);
        }else {
            ctx.fireChannelRead(msg);
        }

    }


    private String AddUserLoginInfoToRedis(UserLoginInfo userLoginInfo) {
        Map<String, String> userLoginInfoMap = new HashMap<String, String>();
        userLoginInfoMap.put(JsonKeyword.USERNAME, userLoginInfo.getUname());
        userLoginInfoMap.put(JsonKeyword.PASSWORD, userLoginInfo.getUpassward());
        userLoginInfoMap.put(JsonKeyword.USER_LOGIN_GAME_INFO, userLoginInfo.getUesr_login_game_info());
        userLoginInfoMap.put(JsonKeyword.USER_LOGIN_TEL, userLoginInfo.getUser_login_tel());
        userLoginInfoMap.put(JsonKeyword.USER_LOGIN_EMAIL, userLoginInfo.getUser_login_email());

        try {
            Jedis jedis =tankJedisPool.getConnection();
            jedis.hmset(userLoginInfo.getUname(), userLoginInfoMap);

            /*查看插入数据*/
            Map<String, String> result = jedis.hgetAll(userLoginInfo.getUname());
            tankJedisPool.putbackConnection(jedis);
            Iterator<Map.Entry<String, String>> it = result.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                System.out.println("key:" + entry.getKey() + " value:"
                        + entry.getValue());
            }
            return "success";
        } catch (Exception e) {
                e.printStackTrace();
                return "flase";
        }

    }

    private int mysqlAdd(String sql, String uname, String upassward) {
        int a = 0;
        int count = 0;
        while (true) {
            try {
                a = C3P0Utils.updata(sql ,uname, upassward);
                logger.info(uname+"通过mysqlAdd访问数据库返回结果"+a);
                break;
            } catch (Exception e) {
                logger.warn(e +uname+ "===>msqlAdd");
                if (count++ >= 2) {
                    logger.error(uname+"暂时不能访问数据库===>mysqlAdd "+a);
                    return a;
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e1) {
                    logger.error(e1 + "thread sleep is error in mysqlAdd");
                }
                continue;
            }
        }
        return a;
    }

    private int mysqlAdd1(String sql1, UserLoginInfo userLoginInfo) {
        int a = 0;
        int count = 0;
        while (true) {
            try {
                a = C3P0Utils.updata(sql1,userLoginInfo.getUser_login_tel(),userLoginInfo.getUname(),
                        userLoginInfo.getUpassward(),userLoginInfo.getUser_login_tel(),
                        userLoginInfo.getUser_login_email(),null);
                logger.info(userLoginInfo.getUname()+"通过mysqlAdd 访问数据库(user_login_info)返回结果"+a);
                break;
            } catch (Exception e) {
                logger.warn(e +userLoginInfo.getUname()+ "===>msqlAdd");
                if (count++ >= 2) {
                    logger.error(userLoginInfo.getUname()+"暂时不能访问数据库===>mysqlAdd "+a);
                    return a;
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e1) {
                    logger.error(e1 + "thread sleep is error in mysqlAdd");
                }
                continue;
            }
        }
        return a;
    }
}
