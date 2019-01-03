package handler;
/*
{"head":"garage","requestType":"image"}  //点击编辑坦克,刷回html
{"head":"garage","requestType":"data",type":"chassis","name":"light",}

{"head":"garage","requestType":"submit","chassis":"light","fire":"flame"} Hash {key="current:userId",field="underPanName",value="light"...}
/*Request:   {“head”:”garage”,”requestType”:”getCurrentTank”}
        */
//{"head":"garage","requestType":"equip","name":"middle"}       Set {key="equip:userId",vlaue=...}
//{"head":"garage","requestType":"buy","name":"xxx"}

//redis hash---------->> key="userId",field="underPanName",value="light"...

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import config.GarageKey;
import config.JsonHeadKey;
import config.StateCode;
import config.pojo.BasedEquip;
import config.pojo.Turret;
import dao.CommonRepertoryEqupment.CommonRepertoryEqupment;
import dao.CommonRepertoryEqupment.CommonRepertoryEqupmentEngine;
import dao.CommonRepertoryEqupment.CommonRepertoryEqupmentTurret;
import dao.UserTankInfo;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import utils.redis.TankJedisPool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetGarageHandler extends ChannelHandlerAdapter {
    private static Logger logger = Logger.getLogger(GetGarageHandler.class);
    private TankJedisPool tankJedisPool ;
    private static final String GET_GARAGE_PAGE_PATH = "/home/qiao/IdeaProjects/tanklegendnetty/src/main/resources/static/test1.html";
    private String type;
    private String name;
    private String userId = "022";
    private UserTankInfo tank = new UserTankInfo();

    public GetGarageHandler(TankJedisPool tankJedisPool) {
        this.tankJedisPool = tankJedisPool;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("start---->>channelRead---->>getGarage");

        String content = (String) msg;
        JSONObject object = JSON.parseObject(content);
        if (object != null && JsonHeadKey.GARAGE.equalsIgnoreCase(object.getString("head"))) {
            String equalParam = object.getString("requestType");
            if (GarageKey.REQUEST_TYPE_1.equalsIgnoreCase(equalParam)) {
                File file = new File(GET_GARAGE_PAGE_PATH);
                if (file.exists()) {
                    System.out.println("文件存在-----");
                    FileReader fileReader = new FileReader(file);
                    BufferedReader br = new BufferedReader(fileReader);
                    StringBuilder builder = new StringBuilder();
                    String str = "";
                    while ((str = br.readLine()) != null) {
                        builder.append(str);
                    }
                    // System.out.println("builder---->>"+builder);
                    ctx.writeAndFlush(builder.toString());

                } else {
                    logger.info("---->>>file未找到...");
                }
            }
            if (GarageKey.REQUEST_TYPE_2.equalsIgnoreCase(equalParam)) {//请求数据及描述
                logger.info("==========================data=========================");
                type = object.getString("type");
                name = object.getString("name");
                if ((type == "") || (type == null)) {
                    type = GarageKey.TYPE_DEFAULT;
                }
                if ((name == "") || (name == null)) {
                    name = GarageKey.NAME_DEFAULT;
                }
                CommonRepertoryEqupment obj = this.getEquip(type, name);
                //logger.info("return obj-------->>"+obj.getName());
                String string = JSON.toJSONString(obj);
                logger.info(string);
                ctx.writeAndFlush(string);
            }
            if (GarageKey.REQUEST_TYPE_3.equalsIgnoreCase(equalParam)) {
                logger.info("=====================getCurrentTank=======================");
                if (userId != null && userId != "") {
                    Jedis jedis=tankJedisPool.getConnection();
                    String key = new StringBuffer("current:").append(userId).toString();
                    Pipeline pipeline = jedis.pipelined();

                    Response<List<String>> response=pipeline.hmget(key,"underPanName","turretName");
                    pipeline.close();
                    String underPan=response.get().get(0);
                    String turret=response.get().get(1);

                    logger.info("recvMsg from redis--->>" + underPan + "  " + turret);
                    tankJedisPool.putbackConnection(jedis);
                    if (underPan != "" && turret != "") {
                        tank.setUnderPanName(underPan);
                        tank.setTurretName(turret);
                        System.out.println(tank.getEupment_engin_id()+tank.getEqupment_turret_id()+"--=--===-=-="+JSON.toJSONString(tank));
                        ctx.writeAndFlush(JSON.toJSONString(tank));
                    } else {
                        ctx.writeAndFlush(StateCode.ERROR);
                    }
                }
            }
            if (GarageKey.REQUEST_TYPE_4.equalsIgnoreCase(equalParam)) {
                logger.info("================equip================");
                String equipName = object.getString("name");
                Jedis jedis=tankJedisPool.getConnection();
                String key = new StringBuffer().append("equip:").append(userId).toString();
                if (jedis.sismember(key, equipName)) {
                    ctx.writeAndFlush(StateCode.OK);
                } else {
                    ctx.writeAndFlush(StateCode.ERROR);
                    logger.info("该用户没有购买该装备....");
                }
                tankJedisPool.putbackConnection(jedis);
            }
            if (GarageKey.REQUEST_TYPE_5.equalsIgnoreCase(equalParam)) {
                logger.info("=============buy===============");
                String equpipName = object.getString("name");
                Jedis jedis=tankJedisPool.getConnection();
                String key = new StringBuffer().append("equip:").append(userId).toString();
                if (equpipName != "") {
                    if (jedis.sadd(key, equpipName) == 1) {
                        ctx.writeAndFlush(StateCode.OK);
                        logger.info("buy and equip success...");
                        tankJedisPool.putbackConnection(jedis);
                    } else {
                        logger.info("buy and equip fail....");
                        ctx.writeAndFlush(StateCode.ERROR);
                    }

                }

            }
            if (GarageKey.REQUEST_TYPE_6.equalsIgnoreCase(equalParam)) {
                logger.info("============================submit=========================");
                String chassis = object.getString("chassis");
                String fire = object.getString("fire");
                StringBuffer buffer = new StringBuffer();
                buffer.append("current:").append(userId);
                Jedis jedis=tankJedisPool.getConnection();
                String key = buffer.toString();
                Map map = new HashMap();
                map.put("underPanName", chassis);
                map.put("turretName", fire);
                jedis.hmset(key, map);
                tankJedisPool.putbackConnection(jedis);
                ctx.writeAndFlush(StateCode.OK);
                logger.info("...................submit successful.............");
            } else {
                ctx.writeAndFlush(StateCode.ERROR);
            }

        }
    }


    public CommonRepertoryEqupment getEquip(String type, String equipName) {
        CommonRepertoryEqupment basedEquip = null;
        switch (type) {
            case GarageKey.CHASSIS:
                switch (equipName) {
                    case GarageKey.LIGHT_CHASSIS:
                        basedEquip = makeChassis("lightChassis", GarageKey.LIGHT_CHASSIS_DESC, 100, 65, 10, 75, 8.2);
                        break;
                    case GarageKey.HEAVY_CHASSIS:
                        basedEquip = makeChassis("heavyChassis", GarageKey.HEAVY_CHASSIS_DESC, 100, 140, 4, 32, 10);
                        break;
                    default:
                        logger.info("默认返回中甲---->>" + GarageKey.MIDDLE_CHASSIS);
                        basedEquip = makeChassis("middleChassis", GarageKey.MIDDLE_CHASSIS_DESC, 150, 90, 7.2, 58, 7.2);
                }
                break;
//            case GarageKey.PROP:
//                switch (equipName) {
//                    case GarageKey.ADD_SPEED:
//                        basedEquip = makeProp("doubleDefeng", GarageKey.ADD_SPEED_DESC, 100, "damage", 123);
//                        break;
//                    case GarageKey.DOUBLE_DEFEND:
//                        basedEquip = makeProp("doubleDamage", GarageKey.DOUBLE_DAMAGE_DESC, 89, "", 0);
//                        break;
//                    default:
//                        logger.info("默认返回双倍伤害---->>" + GarageKey.DOUBLE_DEFEND);
//                        basedEquip = makeProp("addSpeed", GarageKey.ADD_SPEED_DESC, 122, "", 0);
//                }
//                break;
            default:
                switch (equipName) {
                    case GarageKey.ION_FIRE:
                        basedEquip = makeTurret("ionFire", GarageKey.ION_FIRE_DESC, 150, 13, 180, 50, 0);
                        break;
                    case GarageKey.LASER_FIRE:
                        basedEquip = makeTurret("laserFire", GarageKey.LASER_FIRE_DESC, 130, 14, 178, 40, 0);
                        break;
                    default:
                        logger.info("默认返回火焰炮炮塔---->>" + GarageKey.FIRE + "  " + GarageKey.FLAME_FIRE);
                        basedEquip = makeTurret("flameFire", GarageKey.FLAME_FIRE_DESC, 150, 18, 180, 20, 0);
                }

        }
        return basedEquip;
    }

    public CommonRepertoryEqupment makeChassis(String name, String describe, int diamond, double defend, double maxSpeed, double turnSpeed, double power) {
        CommonRepertoryEqupmentEngine commonRepertoryEqupmentEngine = new CommonRepertoryEqupmentEngine();
        commonRepertoryEqupmentEngine.setName(name);
        commonRepertoryEqupmentEngine.setDescription(describe);
        commonRepertoryEqupmentEngine.setDiamond(diamond);
        commonRepertoryEqupmentEngine.setuPDefend(defend);
        commonRepertoryEqupmentEngine.setuPMaxSpeed(maxSpeed);
        commonRepertoryEqupmentEngine.setuPTurnSpeed(turnSpeed);
        commonRepertoryEqupmentEngine.setuPPower(power);//动力
        return commonRepertoryEqupmentEngine;
    }

    public CommonRepertoryEqupment makeTurret(String name, String describe, int diamond, double damage, double reload, double attackRange, double burnTime) {
        CommonRepertoryEqupmentTurret turret = new CommonRepertoryEqupmentTurret();
        turret.setName(name);
        turret.setDescription(describe);
        turret.setDiamond(diamond);
        turret.setTurretDamage(damage);
        turret.setTurretReload(reload);
        turret.setTurretAttackRange(attackRange);
        turret.setTurretBurnTime(burnTime);
        return turret;
    }

//    public BasedEquip makeProp(String name, String desc, int diamond, String ablilityName, double ablilityValue) {
//        Prop prop = new Prop();
//        prop.setName(name);
//        prop.setDescription(desc);
//        prop.setDiamond(diamond);
//        prop.setAbilityName(ablilityName);
//        prop.setAbilityValue(ablilityValue);
//        return prop;
//    }
}
