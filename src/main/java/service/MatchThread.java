package service;

import config.pojo.Player;
import dao.JsonKeyword;
import dao.UserPlayInfo;
import handler.LoginHandler;
import io.netty.channel.socket.SocketChannel;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import utils.redis.TankJedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MatchThread implements Runnable {
    private static Logger logger = Logger.getLogger(MatchThread.class.getName());
    private TankJedisPool tankJedisPool;

    public TankJedisPool getTankJedisPool() {
        return tankJedisPool;
    }

    public void setTankJedisPool(TankJedisPool tankJedisPool) {
        this.tankJedisPool = tankJedisPool;
    }


    @Override
    public void run() {
        while (true) {
            List<Player> list = MatchPlayer.matchProcess(MatchPlayer.playerPool);
            if (list != null) {
                if (list.size() == 2) {
                    for (Player player : list) {
                        SocketChannel channel = (SocketChannel) ChannelMap.takeChannelFromMatchMap(player.getPlayerId() + "");
                        System.out.println("getChannelAdrees=====================>>>>>>>>" + channel);
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(list.get(0).getPlayerId()).append("与").append(list.get(1).getPlayerId());
                        System.out.println("reponseMsg====>>>" + buffer);
                        channel.writeAndFlush(buffer.toString());
                        System.out.println(" ==========writeAndFlush success.");
                    }
                    saveUserMatchRoomid(list);
                }
            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveUserMatchRoomid(List<Player> list) {
        Jedis jedis=tankJedisPool.getConnection();

        jedis.hset(JsonKeyword.ROOMID, list.get(0).getPlayerId(), list.get(1).getPlayerId());
        jedis.hset(JsonKeyword.ROOMID, list.get(1).getPlayerId(), list.get(0).getPlayerId());
        logger.warn("================"+list.get(0).getPlayerId() +"和："+list.get(1).getPlayerId()+"一个房间================");
        logger.info(jedis.hget(JsonKeyword.ROOMID, list.get(0).getPlayerId()));/*等价于命令行中 hget session qiao*/
        logger.info(jedis.hget(JsonKeyword.ROOMID, list.get(1).getPlayerId()));/*等价于命令行中 hget session qiao*/
        tankJedisPool.putbackConnection(jedis);
        logger.info(list.get(0).getPlayerId() + "保存roomid成功.===>saveUserMatchRoomid");
        logger.info(list.get(1).getPlayerId() + "保存roomid成功.===>saveUserMatchRoomid");
    }

    //    private static volatile MatchThread instance = null;
//
//    private MatchThread(TankJedisPool tankJedisPool) {
//        this.tankJedisPool = tankJedisPool;
//    }
//
//    public static MatchThread getInstance(TankJedisPool tankJedisPool) {
//        if (instance == null) {
//            synchronized (MatchThread.class) {
//                instance = new MatchThread(tankJedisPool);
//            }
//        }
//        return instance;
//    }
}

/*匹配池需要完成的事情
*   1.将匹配双方的数据存到redis中，并以roomid username “roomid” 作为当条数据进行存储。
*   2.如果希望开始游戏后能将数据的向前端传输，就得记得玩家开始游戏时的channel，同时有向前端传输数据的线程进行传输数据。
*   */


