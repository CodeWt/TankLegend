package handler;

import com.alibaba.fastjson.JSONObject;
import dao.JsonKeyword;
import dao.UserPlayInfo;
import dao.UserPlayRoom;
import handler.service.TradeUserInfoService;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import utils.FullHttpRequestUtils;
import utils.GetRandom;
import utils.redis.TankJedisPool;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Created by  qiao
 * @date 18-3-11 下午4:20
 */

public class TradeUserInfoPutHandler extends ChannelHandlerAdapter {
    private static Logger logger = Logger.getLogger(TradeUserInfoGetHandler.class);
    private ReentrantLock lock = new ReentrantLock();
    private TankJedisPool tankJedisPool;
    /*所有房间*/
    private UserPlayRoom userPlayRoom = new UserPlayRoom();

    public TradeUserInfoPutHandler(TankJedisPool tankJedisPool) {
        this.tankJedisPool = tankJedisPool;
    }

    /**
     *
     * @param ctx
     * @param msg
     *
     * 前端 点击    准备   时  将user数据插入到redis中.
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        String uri=FullHttpRequestUtils.getUri(msg);
        if (uri.equals("TradeUserInfoPut")) {
            lock.lock();
            JSONObject body=FullHttpRequestUtils.ContentToJson(msg);
            boolean StatusFlag=false;
            String username = body.getString(JsonKeyword.USERNAME);
            String sessionid = body.getString(JsonKeyword.SESSION);
            int roomid = body.getInteger(JsonKeyword.ROOMID);

            if(canLogin(username,sessionid)){/*canLogin(username,passwd)*/
                /*在redis中查找并返回结果*/
                StatusFlag =true;
                userPlayRoom.setRoomid(roomid);
            }else {
                logger.debug( "用户名登录异常.");
            }

            ctx.writeAndFlush("[欢迎"+username+"]:进入房间 ("+StatusFlag+")!!").addListener(ChannelFutureListener.CLOSE);
            lock.unlock();
            logger.info("["+username+"]: 进入房间("+StatusFlag+")!!");
        }else {
            ctx.fireChannelRead(msg);
        }

    }

    private boolean canLogin(String username, String sessionid) {
        return true;
    }

}
