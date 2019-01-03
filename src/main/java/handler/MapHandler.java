package handler;


import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.SerializerFeature;
import dao.CommonMap.MapLoad;
import dao.JsonKeyword;
import dao.UserMapInfo;
import handler.service.TradeUserInfoService;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import server.PSServer;
import service.ChannelMap;
import utils.FullHttpRequestUtils;
import utils.redis.TankJedisPool;


import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import static dao.JsonKeyword.*;

public class MapHandler extends ChannelHandlerAdapter {
    private static Logger logger = Logger.getLogger(MapHandler.class.getName());
    private ReentrantLock lock = new ReentrantLock();
    private TankJedisPool tankJedisPool;
    private UserMapInfo userMapInfo=new UserMapInfo();
    private List mapInfoArrayList1=new ArrayList<>(MAXNUMBMAP);
    public MapHandler(TankJedisPool tankJedisPool) {
        this.tankJedisPool=tankJedisPool;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws JSONException, UnsupportedEncodingException {
        String uri= FullHttpRequestUtils.getUri(msg);
        if (uri.equals("Map")) {
            JSONObject body=FullHttpRequestUtils.ContentToJson(msg);
            userMapInfo.setRoom_id(body.getString(JsonKeyword.ROOMID));
            userMapInfo.setUsername(body.getString(JsonKeyword.USERNAME));
            /*将传进来的msg中mapinfo转换成Array,将这个array转化成一个对象.*/
            JSONArray mapinfoid1 = body.getJSONArray(JsonKeyword.MAPINFOID);
            String js = JSONObject.toJSONString(mapinfoid1, SerializerFeature.WriteClassName);

            List mapInfoArrayList=new ArrayList<>(MAXNUMBMAP);
            /*将每一个地图转化成Mapload对象。*/
            mapInfoArrayList =JSONObject.parseArray(js,MapLoad.class);
            for(int i = 0 ; i < mapInfoArrayList.size() ; i++) {
                System.out.println("==========================================");
                System.out.println(mapInfoArrayList.get(i).toString());
                JSONObject jsonpObject= (JSONObject) mapInfoArrayList.get(i);
                MapLoad mapLoad = new MapLoad();
                mapLoad.setRoom_id(body.getString(JsonKeyword.ROOMID));
                mapLoad.setId(jsonpObject.getInteger("id"));
                mapLoad.setMap_id(jsonpObject.getString("map_id"));
                mapLoad.setType(jsonpObject.getString("type"));
                mapInfoArrayList1.add(i,mapLoad);
            }
            /*可以获取地图列表*/
            MapLoad ml = (MapLoad) mapInfoArrayList1.get(3);
            logger.info("mapInfoArrayList1"+ml.toString());
            userMapInfo.setMapLoads(mapInfoArrayList);
            logger.info(userMapInfo.toString() + "访问后台返回值===>MapHandler:channelRead");
            ChannelMap.putChannelToMatchMap(userMapInfo.getUsername(),(SocketChannel) ctx.channel());
            ctx.writeAndFlush(userMapInfo.toString()).addListener(ChannelFutureListener.CLOSE);
//            addUserMapInfo(mapInfoArrayList1);
            AddUserMapInfoToRedis(mapInfoArrayList1);
            AddUserMapInfoToRedis(js);
        } else {
            ctx.fireChannelRead(msg);
        }

    }

    /*将地图中的每一块以map的形式写入redis中。*/
    private String AddUserMapInfoToRedis(List mapInfoArrayList) {
        Jedis jedis =tankJedisPool.getConnection();
        Map<String, String> userMaps = new HashMap<>();
        logger.info("AddUserMapInfoToRedis start");
        for(int i = 0 ; i < userMapInfo.getMapLoads().size() ; i++) {
            MapLoad mapLoad=null;
            mapLoad = (MapLoad) mapInfoArrayList.get(i);
            userMaps.put(String.valueOf(mapLoad.getId()), mapLoad.toString());
            System.out.println(mapLoad.toString());
        }
        logger.info("AddUserMapInfoToRedis end");
        /*插入到redis中     roomid  map */
        jedis.hmset(userMapInfo.getRoom_id(), userMaps);

        /*获取room中的所有信息。*/
        Map<String, String> result = jedis.hgetAll(userMapInfo.getRoom_id());
        tankJedisPool.putbackConnection(jedis);
        Iterator<Map.Entry<String, String>> it = result.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("key:" + entry.getKey() + " value:"
                    + entry.getValue());
        }
        return "success";
    }

    /*将整个地图直接插入的redis中，以便于前端js进行解析。*/
    private String AddUserMapInfoToRedis(String json) {
        Jedis jedis =tankJedisPool.getConnection();
        jedis.hset(JsonKeyword.MAPINFOKEY,userMapInfo.getRoom_id(), json);
        tankJedisPool.putbackConnection(jedis);
        return "a";
    }

}

/*insert mysql*/
//    public void addUserMapInfo(List mapInfoArrayList) {
//        int rows = 0;
//            QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
//            String sql=null;
//            MapLoad mapLoadone=null;
//            for(int i=0;i<mapInfoArrayList.size();i++) {
//                try {
//                    sql = "insert into user_map_list values(null,?,?,?,?)";
//                    /*数组转化为javabean*/
//                    String str = mapInfoArrayList.get(i).toString();
//                    mapLoadone = JSON.parseObject(str, MapLoad.class);
//                    mapLoadone.setUser_id(userMapInfo.getUser_id());
//                    System.out.println("---------------+"+mapLoadone.toString());
//                    Object[] params = {mapLoadone.getId(),mapLoadone.getUser_id(),  mapLoadone.getType(), mapLoadone.getMap_id()};
//
//                    rows = qr.update(sql, params);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (rows > 0) {
//                        logger.info("user_map_list添加成功");
//                    } else {
//                        System.out.println("添加失败!");
//                        logger.warn("user_map_list添加失败");
//                    }
//                }
//            }
//    }

//            lock.lock();
//            PSServer.userPlayInfo.setUser_id(userMapInfo.getUser_id());
//            PSServer.userPlayInfo.setUserMapInfo(userMapInfo);
//            TradeUserInfoService tradeUserInfoService = new TradeUserInfoService(JsonKeyword.MAPINFO);
//            tradeUserInfoService.put(userMapInfo.getUser_id());
//            lock.unlock();

/*
*   地图的收集
*       同一个房间的地图应该一样。所以就把map存入到redis中  需要做 但是需要一个默认地图。
* */