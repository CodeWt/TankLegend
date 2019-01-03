package service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import dao.JsonKeyword;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import utils.redis.TankJedisPool;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Created by  qiao
 * @date 18-4-8 下午9:17
 */

public class LoginService {
    private static Logger logger = Logger.getLogger(LoginService.class.getName());
    private ReentrantLock lock = new ReentrantLock();

    private TankJedisPool tankJedisPool;
    private static int i = 0;

    public LoginService(TankJedisPool tankJedisPool) {
        this.tankJedisPool =tankJedisPool;
        System.out.println("================进入LoginService的构造函数.=================");
    }

    public TankJedisPool getTankJedisPool() {
        return tankJedisPool;
    }

    public void setTankJedisPool(TankJedisPool tankJedisPool) {
        this.tankJedisPool = tankJedisPool;
    }

    public String  channelRead(JSONObject body) throws JSONException, UnsupportedEncodingException {
//        JSONObject body = (JSONObject)msg;
        System.out.println("开始LoginService 之旅!");
        String type = body.getString(JsonKeyword.TYPE);
        if(type.equalsIgnoreCase(JsonKeyword.LOGIN)){
            String username = body.getString(JsonKeyword.USERNAME);
            String passwd = body.getString(JsonKeyword.PASSWORD);
            String sessionid = null ;
            Cookie info =new Cookie("user","aaaaaa");
            info.setValue("aaaaaa");
            String can = "0";
            if(true){//canLogin(username,passwd)
                boolean a =canLogin(username,passwd);
                System.out.println("====================>结果是: "+a);
                can = "1";
            }
            logger.info(username+"访问后台返回值===>Loginhandler:channelRead"+can);
            String aaa =can+", cookie" +info;
            lock.lock();
            System.out.println(body+" :: "+(i++));
            lock.unlock();
                return aaa +"我是LoginService";
        }
        logger.warn("============>LoginService.channel has ERROR.  <============");
            return "============>Login  ERROR !! <============";
    }

    private boolean canLogin(String username,String passwd){


        System.out.println("==================================canLogin");
        System.out.println(username+"  "+passwd+"  ");
        System.out.println("==================================canLogin");

        Jedis jedis =null;
        jedis=tankJedisPool.getConnection();

        int count = 0;

        while (count < 3) {
            try{

//                QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
//                // 2.编写SQL语句
////                String sql = "insert into tbl_user values(null,?,?)";
//                String sql1 = "select * from tbl_user where uname=?";
//
//                // 3.为站位符设置值
////                Object[] params = { "aa", "bb" };
//                Object[] params1 = {};
//
//                // 4.执行添加操作
////                int rows = 0;
////                try {
////                    rows = qr.update(sql, params);
////                    if (rows > 0) {
////                        System.out.println("添加成功!");
////                    } else {
////                        System.out.println("添加失败!");
////                    }
////                } catch (SQLException e) {
////                    e.printStackTrace();
////                }
//                //执行查询操作
////                User user = new User();
//                try {
//                    user = qr.query(sql1, new BeanHandler<User>(User.class),username);
//                    System.out.println(user.getUname() + " : " + user.getUpassward());
//
//
//                    if(user.getUpassward()!=null && user.getUpassward().equals(passwd)){
//                        logger.info(username+"===>canLogin true");
//                        System.out.println("========================");
//                        System.out.println(username+"666  "+passwd+"  ");
//                        System.out.println("========================");
//                        return true;
//                    }
//                    logger.info(username+"===>canLogin false");
//
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }



//                jedis.hset(JsonKeyword.USERS, JsonKeyword.USERNAME, username);
//                jedis.hset(JsonKeyword.USERS,JsonKeyword.PASSWORD,passwd);
//                jedis.hset(JsonKeyword.USERS,JsonKeyword.CHECKCODE, checkcode);
//                jedis.hget(JsonKeyword.USERS,username);
//                if(passwd0 != null && passwd0.equals(passwd)){
//                    tankJedisPool.putbackConnection(jedis);
//                    logger.info(username+"===>canLogin true");
//                    return true;
//                }
                System.out.println("=================>"+jedis.hget(JsonKeyword.USERS,JsonKeyword.USERNAME));
                System.out.println("=================>"+jedis.hget(JsonKeyword.USERS,JsonKeyword.PASSWORD));

//                Map<String, String> car = new HashMap<String, String>();
//                car.put("COLOR", "red");
//                car.put("SIZE", "2T");
//                car.put("NUM", "100");
//                car.put("NO", "京8888");
//                // 存入对象，使用car:01当作key，是为了方便和其他car区分。比如car:02
//                jedis.hmset("car:01", car);

                tankJedisPool.putbackConnection(jedis);
                logger.info(username+"===>canLogin false");
                return false;
            }catch(JedisConnectionException e){
                tankJedisPool.repairConnection(jedis);
                logger.warn(e+"===>login : "+"redis connection down!");
                count ++;
                if(count >= 3){
                    tankJedisPool.putbackConnection(jedis);
                    logger.info(username+"暂时无法访问redis===>canLogin false");
                    return false;
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e1) {
                    logger.error(e1+"thread sleep is error in canLogin");
                }
            }
        }
        return false;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();;
        ctx.close();
    }

}
