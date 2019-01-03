package service;

import config.pojo.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.redis.TankJedisPool;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class MatchPlayer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchPlayer.class);
    private TankJedisPool tankJedisPool;
    private static final int maxRange=50;
    private static final int minRange=0;
    //每个人需要匹配到的玩家数量
    private static int NEED_MATCH_PALYER_COUNT = 2;
    //匹配池
    public static ConcurrentHashMap<String, Player> playerPool = new ConcurrentHashMap<>();

    //把玩家放入匹配池
    public static void putPlayerIntoMatchPool(String playerId, int rank) {
        Player playerInfo = new Player(playerId, rank);
        playerPool.put(playerId,playerInfo);
    }

    public static void setTankJedisPool(TankJedisPool tankJedisPool) {
        tankJedisPool = tankJedisPool;
    }

    public TankJedisPool getTankJedisPool() {
        return tankJedisPool;
    }

    //把玩家从匹配池删除
    public static void removePlayerFromMatchPool(String playerId) {
        playerPool.remove(playerId);
    }

    public static List<Player> matchProcess(ConcurrentHashMap<String, Player> playerPool) {
        long startTime = System.currentTimeMillis();
        //LOGGER.info("执行匹配开始|开始时间  " + startTime);
        //创建一个TreeMap用于存放不同key（rank）所对应的同一等级的集合value（set）
        TreeMap<Integer, HashSet<Player>> pointMap = new TreeMap<>();
        for (Player matchPlayer : playerPool.values()) {//该循环将不同rank等级的玩家分别放入其rank所对应的set 也就是一个等级 一个set
            //在匹配池中时间太长  直接删除
            if ((System.currentTimeMillis() - matchPlayer.getStartMatchTime()) > 3 * 60 * 1000) {
                //LOGGER.warn(matchPlayer.getPlayerId() + "timeout in matchPool so remove directly");
                /*player匹配与出自己以外的在线用户。*/
                removePlayerFromMatchPool(matchPlayer.getPlayerId());
                continue;
            }
            HashSet<Player> set = pointMap.get(matchPlayer.getRank());
            if (set == null) {
                //该等级池中尚未有玩家，创建一个该rank级别的set集合
                set = new HashSet<>();
                set.add(matchPlayer);
                pointMap.put(matchPlayer.getRank(), set);
            } else {
                //添加到该rank所属集合set中
                set.add(matchPlayer);
            }
        }
        for (HashSet<Player> sameRankPlayersSet : pointMap.values()) {
            boolean continueMatch = true;
            while (continueMatch) {
                //find the longest player from the range of same rank and use him to match
                //if he is not match,others is harder to match
                Player oldest = null;
                for (Player player : sameRankPlayersSet) {
                    if (oldest ==null) {
                        oldest = player;
                    } else if (player.getStartMatchTime() < oldest.getStartMatchTime()) {
                        oldest = player;
                    }
                }
                if (oldest == null) {
                    break;
                }
//                LOGGER.info(oldest.getPlayerId() + "  |" +
//                        "match for the longest player of waiting time in this range rank" + oldest.getRank());
                long now = System.currentTimeMillis();
                int waitSecond = (int) ((now - oldest.getStartMatchTime()) / 1000);
//                LOGGER.info(oldest.getPlayerId() + "当前时间已经等待时间|waitSecond|" + waitSecond
//                        + "|当前系统时间" + now + "|开始匹配时间|" + oldest.getStartMatchTime());

                int middle = oldest.getRank();
                List<Player> matchPair = new ArrayList<>();
                //从中位数向两边扩大搜索范围
                for (int searchRankUp = middle, searchRankDown = middle; searchRankUp <= maxRange || searchRankDown > minRange; searchRankUp++, searchRankDown--) {
                    HashSet<Player> thisRankPlayers = pointMap.getOrDefault(searchRankUp, new HashSet<>());
                    if (searchRankDown != searchRankUp && searchRankDown > 0) {
                        thisRankPlayers.addAll(pointMap.getOrDefault(searchRankDown, new HashSet<>()));
                    }
                    if (!thisRankPlayers.isEmpty()) {
                        if (matchPair.size() < NEED_MATCH_PALYER_COUNT) {
                            Iterator<Player> it = thisRankPlayers.iterator();
                            while (it.hasNext()) {
                                Player player = it.next();
                                if (player.getPlayerId() != oldest.getPlayerId()) {//排除玩家本身                              }
                                    if (matchPair.size() < NEED_MATCH_PALYER_COUNT) {
                                        matchPair.add(player);
                                        matchPair.add(oldest);
                                        LOGGER.info("oldedst id:"+oldest.getPlayerId() + "==|rank|==>>"+oldest.getRank()+"|匹配到玩家的id为:" + player.getPlayerId()
                                                + "|rank|===>>" + player.getRank()+"   poolsSize==>>"+playerPool.size()+"  rank  "+pointMap.get(player.getRank()).size());
                                        //删除
                                        it.remove();
                                        playerPool.remove(player.getPlayerId());
                                        playerPool.remove(oldest.getPlayerId());
                                        pointMap.get(player.getRank()).remove(player);
                                        pointMap.get(player.getRank()).remove(oldest);
                                        LOGGER.info("poolsSize===>>"+playerPool.size()+"rank====>>"+pointMap.get(player.getRank()).size());
                                        return matchPair;
                                    } else {
                                        break;
                                    }
                                }
                            }
                        } else {
                            break;
                        }
                    }
                }
            }

        }
        long endTime=System.currentTimeMillis();

        LOGGER.debug("执行匹配结束|结束时间| "+endTime+"|耗时|"+(endTime-startTime)+"ms");
        return null;
    }

}
