package handler;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import dao.UserLoginInfo;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.log4j.Logger;

import dao.JsonKeyword;
import redis.clients.jedis.Jedis;
import utils.C3P0Utils;
import utils.FullHttpRequestUtils;
import utils.GetRandom;
import utils.redis.TankJedisPool;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class LoginHandler extends ChannelHandlerAdapter {
    private static Logger logger = Logger.getLogger(LoginHandler.class.getName());
    private TankJedisPool tankJedisPool;
    /*查看访问了几次*/
    private static int i = 0;
    private static UserLoginInfo userLoginInfo = new UserLoginInfo();

    public LoginHandler(TankJedisPool tankJedisPool) {this.tankJedisPool=tankJedisPool;
    }

    public void  channelRead(ChannelHandlerContext ctx, Object msg) {
        String uri=FullHttpRequestUtils.getUri(msg);
        if (uri.equals("Login")) {
            JSONObject body=FullHttpRequestUtils.ContentToJson(msg);
            boolean StatusFlag=false;
            String username = body.getString(JsonKeyword.USERNAME);
            String passwd = body.getString(JsonKeyword.PASSWORD);
            String sessionid = body.getString(JsonKeyword.SESSION);

            System.out.println(body.toJSONString());
            System.out.println("=========================11111");
            JSONObject jsonObject=FullHttpRequestUtils.ContentToMap(msg);

            /*判断用户名和密码是否正确*/
            /*判断user用户的session是否正确 如果不正确 则给一个新的session.如果正确就直接登陆.*/
            /*给每一个登录的用户赋予一个session*/
            if(canLogin(username,passwd)){/*canLogin(username,passwd)*/
                sessionid = GetRandom.getUserId();
                StatusFlag =true;
            }else {
                logger.debug( "用户名或密码错误.");
                ctx.writeAndFlush(StatusFlag).addListener(ChannelFutureListener.CLOSE);
            }
            ctx.writeAndFlush("["+username+"]: 登陆成功!!").addListener(ChannelFutureListener.CLOSE);
            logger.info("["+username+"]: 登陆成功("+StatusFlag+")!!");
            saveUserInfo(username, sessionid);
        }else {
            ctx.fireChannelRead(msg);
        }

    }

    private void saveUserInfo(String username, String sessionid) {
        Jedis jedis=tankJedisPool.getConnection();
        Map<String, String> userInfo = new HashMap<String, String>();
        userInfo.put(JsonKeyword.USERNAME, username);
        userInfo.put(JsonKeyword.SESSION, sessionid);
        boolean status =false;

        jedis.hset(JsonKeyword.SESSION, username, sessionid);
        logger.warn("================"+username +"的Session为："+sessionid+"================");
        logger.info(jedis.hget(JsonKeyword.SESSION, username));/*等价于命令行中 hget session qiao*/
        tankJedisPool.putbackConnection(jedis);
        logger.info(username + "保存UserSession成功.===>saveUserInfo");
    }

    private boolean canLogin(String username, String passwd){

        System.out.println("==================================canLogin");
        System.out.println(username+"  "+passwd+"  ");
        System.out.println("==================================canLogin");
        int count = 0;

        while (count < 3) {
                QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
                // 2.编写SQL语句
//                String sql = "insert into tbl_user values(null,?,?)";
                String sql1 = "select * from tbl_user where uname=?";
                // 3.为站位符设置值
//                Object[] params = { "aa", "bb" };
                Object[] params1 = {};
                // 4.执行添加操作
//                int rows = 0;
//                try {
//                    rows = qr.update(sql, params);
//                    if (rows > 0) {
//                        System.out.println("添加成功!");
//                    } else {
//                        System.out.println("添加失败!");
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
                //执行查询操作
//                User user = new User();
                try {
                    userLoginInfo = qr.query(sql1, new BeanHandler<UserLoginInfo>(UserLoginInfo.class),username);
                    System.out.println(userLoginInfo.getUname() + " : " + userLoginInfo.getUpassward());
                    if(userLoginInfo.getUpassward()!=null && userLoginInfo.getUpassward().equals(passwd)){
                        logger.info(username+"===>canLogin true");
                        System.out.println("========================");
                        System.out.println(username+"  "+passwd+"  ");
                        System.out.println("========================");
                        return true;
                    }
                    logger.info(username+"===>canLogin false");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return false;
        }
        return false;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();;
        ctx.close();
    }

}
