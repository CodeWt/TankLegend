package handler.service;

import dao.JsonKeyword;
import dao.UserPlayInfo;
import org.apache.log4j.Logger;
import server.PSServer;

import java.util.concurrent.*;

import static java.lang.String.valueOf;


/**
 * @Created by  qiao
 * @date 18-3-10 下午7:28
 */

public class TradeUserInfoService {
    private static Logger logger =Logger.getLogger(TradeUserInfoService.class);

    private UserPlayInfo userPlayInfo;

    private String type;

    private int user_id;

    public  final ConcurrentHashMap<Integer, UserPlayInfo> cache =new ConcurrentHashMap<>();

    public TradeUserInfoService() {
    }

    public TradeUserInfoService(String type) {
        this.type = type;
    }

    public String put(final Integer key) {
        //获得接收的类的类型
        //判断是那个user_id和三种信息.

    if(null!=type){
        switch (type){
            case JsonKeyword.MAPINFO :
                logger.info("接受的信息为:"+type+", 接受完毕!!   "+ PSServer.userPlayInfo.toString());
                break;
            case JsonKeyword.TANKINFO:
                logger.info("接受的信息为:"+type+", 接受完毕!!   "+ PSServer.userPlayInfo.toString());
                break;
            case JsonKeyword.TANKCODE:
                logger.info("接受的信息为:"+type+", 接受完毕!!   "+ PSServer.userPlayInfo.toString());
                break;
        }
    }

        //判断UserPlayInfo是否全都收集,如果收集齐,则插入到Hashmap中.
//        if(PSServer.userPlayInfo.getUserTankCode().getUser_id() == PSServer.userPlayInfo.getUserMapInfo().getUser_id() && PSServer.userPlayInfo.getUserTankCode().getUser_id() == PSServer.userPlayInfo.getUserTankInfo().getUser_id()
//                && PSServer.userPlayInfo.getUserTankCode().getUser_id() == PSServer.userPlayInfo.getUserTankInfo().getUser_id()){
//
//
//
//        }

        if (null != PSServer.userPlayInfo.getUserTankCode() && null != PSServer.userPlayInfo.getUserTankInfo() && null != PSServer.userPlayInfo.getUserMapInfo()) {
            cache.put(key,PSServer.userPlayInfo);
            System.out.println("################################success");
            return "success";
        }
        System.out.println("################################false");

        return "false";
    }

    public void get(final Integer key){
        System.out.println("################################cache.toString");
        System.out.println(cache.size()+"   11111111111");
        System.out.println("\t"+cache.get(key).toString());
        System.out.println("################################cache.toString");
    }
}
