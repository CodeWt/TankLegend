package service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
public class  ChannelMap {
    public static Map<String,SocketChannel> mapMatch=new ConcurrentHashMap<>();
    public static Map<String, SocketChannel> mapPlaying = new ConcurrentHashMap<>();

    public static void putChannelToMatchMap(String username,SocketChannel channel){
        mapMatch.put(username,channel);
    }
    public static Channel takeChannelFromMatchMap(String username){
        return mapMatch.get(username);

    }
    public static void putChannelToPlayingMap(String username,SocketChannel channel){
        mapPlaying.put(username,channel);
    }
    public static Channel takeChannelFromPlayingMap(String username){
        return mapPlaying.get(username);

    }

    public static void removeMapMatch(SocketChannel socketChannel){
        for (Map.Entry entry:mapMatch.entrySet()){
            if (entry.getValue()==socketChannel){
                mapMatch.remove(entry.getKey());
            }
        }
    }
    public static void removeMapPlaying(SocketChannel socketChannel){
        for (Map.Entry entry:mapPlaying.entrySet()){
            if (entry.getValue()==socketChannel){
                mapPlaying.remove(entry.getKey());
            }
        }
    }

}


/*
* 绑定channel 与用户  放到一个map中
* 可以用来给客户端指定响应的channel中。
*
* 用途：
*   1.匹配游戏时，成功后给玩家会状态信息。
*   2.编辑信息阶段（不需要)
*   3.开始游戏后，服务端给 房间中的用户发送数据
 *      地图，
 *      tank位置（tank的初始位置以及tank下一步的指令），
 *      游戏胜负。
*
*  tank数据接受。
*
*  map地图的编辑。
*  如何动态的执行tankcode。根据自己的tank信息，与数据库的交互。
* */

