package handler;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import dao.CommonRepertoryEqupment.CommonRepertoryEqupmentEngine;
import dao.CommonRepertoryEqupment.CommonRepertoryEqupmentTurret;
import dao.CommonRepertoryEqupment.CommonRepertoryEqupmentWhell;
import dao.JsonKeyword;
import dao.UserTankInfo;
import handler.service.TradeUserInfoService;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import server.PSServer;
import utils.C3P0Utils;
import utils.FullHttpRequestUtils;
import utils.redis.TankJedisPool;



import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class TankHandler extends ChannelHandlerAdapter {
    private static Logger logger = Logger.getLogger(TankHandler.class.getName());
    private UserTankInfo userTankInfo = new UserTankInfo();
    private TankJedisPool tankJedisPool;
    private ReentrantLock lock = new ReentrantLock();

    public TankHandler(TankJedisPool tankJedisPool) {
        this.tankJedisPool = tankJedisPool;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws JSONException, UnsupportedEncodingException {
        String uri = FullHttpRequestUtils.getUri(msg);
        if (uri.equals("Tank")) {
            JSONObject body = FullHttpRequestUtils.ContentToJson(msg);
            userTankInfo.setUsername(body.getString(JsonKeyword.USERNAME));
            userTankInfo.setSession(body.getString(JsonKeyword.SESSION));
            //将接收到的数据存到内存 并存到redis.
            recTankInfo(body);
            logger.info(userTankInfo.toString() + "访问后台返回值===>TankHandler:channelRead");
            ctx.writeAndFlush(userTankInfo.toString()).addListener(ChannelFutureListener.CLOSE);
            addUserTankInfo();
            PSServer.userPlayInfo.setUser_id(userTankInfo.getUser_id());
            PSServer.userPlayInfo.setUserTankInfo(userTankInfo);
            TradeUserInfoService tradeUserInfoService = new TradeUserInfoService(JsonKeyword.TANKCODE);
            tradeUserInfoService.put(userTankInfo.getUser_id());
        } else {
            ctx.fireChannelRead(msg);
        }

    }


    /*接受数据*/
    public UserTankInfo recTankInfo(JSONObject body) {
        System.out.println("===================123");

        userTankInfo.setEqupment_armour_id(body.getInteger(JsonKeyword.EQUPMENTARMOURID));
        userTankInfo.setEupment_engin_id(body.getString(JsonKeyword.EQUPMENTENGINID));
        userTankInfo.setEqupment_turret_id(body.getString(JsonKeyword.EQUPMENTTURRETID));
        userTankInfo.setEqupment_whell_id(body.getInteger(JsonKeyword.EQUPMENTWHELLID));
        System.out.println("==========" + "getUserTank()");
        /*存到redis*/
        String addUserTankInfoToRedisStr = addUserTankInfoToRedis(userTankInfo);
        /*存到mysql*/
        getUserTank();
        logger.info("---------------" + userTankInfo.toString());
        System.out.println("==========" + "getUserTank()");
        return userTankInfo;
    }

    /*调用数据库中的信息.*/
    public UserTankInfo getUserTank() {

        try {
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            String sqlturret = "select * from common_repertory_equpment_turret where equpment_turret_id=?";
            Object[] paramsturret = {userTankInfo.getEqupment_turret_id()};
            CommonRepertoryEqupmentTurret turret = new CommonRepertoryEqupmentTurret();
            turret = qr.query(sqlturret, new BeanHandler<CommonRepertoryEqupmentTurret>(CommonRepertoryEqupmentTurret.class), paramsturret);
            turret.toString();
            logger.info("====================commonRepertoryEqupmentTurret--------------->" + turret.toString());

            String sqlwhell = "select * from common_repertory_equpment_whell where equpment_whell_id=?";
            Object[] paramswhell = {userTankInfo.getEqupment_whell_id()};
            CommonRepertoryEqupmentWhell whell = new CommonRepertoryEqupmentWhell();
            whell = qr.query(sqlwhell, new BeanHandler<CommonRepertoryEqupmentWhell>(CommonRepertoryEqupmentWhell.class), paramswhell);
            whell.toString();
            logger.info("====================CommonRepertoryEqupmentWhell--------------->" + whell.toString());

            String sqlEngine = "select * from common_repertory_equpment_engine where equpment_engine_id=?";
            Object[] paramsEngine = {userTankInfo.getEupment_engin_id()};
            CommonRepertoryEqupmentEngine engine = new CommonRepertoryEqupmentEngine();
            engine = qr.query(sqlEngine, new BeanHandler<CommonRepertoryEqupmentEngine>(CommonRepertoryEqupmentEngine.class), paramsEngine);
            engine.toString();
            logger.info("====================CommonRepertoryEqupmentEngine--------------->" + engine.toString());

            String sqlArmour = "select * from common_repertory_equpment_armour where equpment_armour_id=?";
            Object[] paramsArmour = {userTankInfo.getEqupment_armour_id()};
            CommonRepertoryEqupmentTurret armour = new CommonRepertoryEqupmentTurret();
            armour = qr.query(sqlArmour, new BeanHandler<CommonRepertoryEqupmentTurret>(CommonRepertoryEqupmentTurret.class), paramsArmour);
            armour.toString();
            logger.info("====================commonRepertoryEqupmentArmour--------------->" + armour.toString());

            userTankInfo.setHP(turret.getHP() + whell.getHP() + engine.getHP() + armour.getHP());
            userTankInfo.setFire(turret.getFire() + whell.getFire() + engine.getFire() + armour.getFire());
            userTankInfo.setShotsSpeed(turret.getShotspeed() + whell.getShotspeed() + engine.getShotspeed() + armour.getShotspeed());
            userTankInfo.setTankSpeed(turret.getTankspeed() + whell.getTankspeed() + engine.getTankspeed() + armour.getTankspeed());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userTankInfo;
    }

    /*写入jdbc数据库*/
    public void addUserTankInfo() {
        int rows = 0;
        try {
            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
            String sql = "insert into user_tank_info values(null,?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {userTankInfo.getUser_id(), userTankInfo.getUser_tank_info_id(), userTankInfo.getEqupment_turret_id(),
                    userTankInfo.getEqupment_whell_id(), userTankInfo.getEupment_engin_id(), userTankInfo.getEqupment_armour_id(), userTankInfo.getHP(),
                    userTankInfo.getFire(), userTankInfo.getShotsSpeed(), userTankInfo.getTankSpeed()};
            rows = qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rows > 0) {
                logger.info("user_tank_info添加成功");
            } else {
                System.out.println("添加失败!");
                logger.warn("user_tank_info添加失败");
            }
        }
    }

    /*写入redis数据库*/
    public String addUserTankInfoToRedis(UserTankInfo userTankInfo) {
        Jedis jedis =tankJedisPool.getConnection();
        Map<String, String> userTankInfoMap = new HashMap<>();
        userTankInfoMap.put(JsonKeyword.USERTANKINFOID, String.valueOf(userTankInfo.getUser_tank_info_id()));
        userTankInfoMap.put(JsonKeyword.EQUPMENTTURRETID, String.valueOf(userTankInfo.getEqupment_turret_id()));
        userTankInfoMap.put(JsonKeyword.EQUPMENTARMOURID, String.valueOf(userTankInfo.getEqupment_armour_id()));
        userTankInfoMap.put(JsonKeyword.EQUPMENTWHELLID, String.valueOf(userTankInfo.getEqupment_whell_id()));
        userTankInfoMap.put(JsonKeyword.EQUPMENTENGINID, String.valueOf(userTankInfo.getEupment_engin_id()));

        try {
            jedis.hmset(userTankInfo.getUsername(), userTankInfoMap);

            /*查看插入数据*/
            Map<String, String> result = jedis.hgetAll(userTankInfo.getUsername());
            Iterator<Map.Entry<String, String>> it = result.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                System.out.println("key:" + entry.getKey() + " value:"
                        + entry.getValue());
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return "flase";
        }
    }


    /*返回时需要吧坦克的属性都返回回去,不过也可以写一个前端写一个js文件直接显示.*/

}
