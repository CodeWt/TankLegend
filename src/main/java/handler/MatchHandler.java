package handler;

import com.alibaba.fastjson.JSONObject;
import config.JsonHeadKey;
import config.StateCode;
import dao.JsonKeyword;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ChannelMap;
import service.MatchPlayer;
import service.MatchThread;
import service.ThreadPoolUtils;
import utils.FullHttpRequestUtils;
import utils.redis.TankJedisPool;
import java.util.concurrent.TimeUnit;



//{"head":"match"}
public class MatchHandler extends ChannelHandlerAdapter{
    private TankJedisPool tankJedisPool;
    private static final MatchThread matchThread=new MatchThread();
    private static int id=0;
    private static final Logger logger = LoggerFactory.getLogger(MatchHandler.class);

    public MatchHandler(TankJedisPool tankJedisPool) {
        this.tankJedisPool = tankJedisPool;
        matchThread.setTankJedisPool(tankJedisPool);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String uri= FullHttpRequestUtils.getUri(msg);
        if(uri.equals("Match")){
            logger.info("=================MatchPlayer======================");
            String playerId="22"+(++id);
            JSONObject body=FullHttpRequestUtils.ContentToJson(msg);
            if (body!=null&& JsonHeadKey.MATCH.equalsIgnoreCase(body.getString("head"))){
                System.out.println(body.toJSONString());
                String username = body.getString(JsonKeyword.USERNAME);
                logger.info("==========================into matcherPools============================");
                ChannelMap.putChannelToMatchMap(username,(SocketChannel) ctx.channel());
                System.out.println("===============================================================11");
//                MatchPlayer.setTankJedisPool();
                MatchPlayer.putPlayerIntoMatchPool(username,35+id);
                System.out.println("===============================================================22");
                TimeUnit.SECONDS.sleep(5);
                //ChannelMap.takeChannelFromMap("0221").writeAndFlush(StateCode.OK);
                //ctx.channel().writeAndFlush(StateCode.OK);
//                new Thread(matchThread).start();
                ThreadPoolUtils.getMatchPool().submit(matchThread);

            }else {
                ctx.channel().writeAndFlush(StateCode.ERROR);
            }

        }else {
//            ctx.fireChannelRead(msg);
        }


    }
}
