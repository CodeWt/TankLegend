package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

public class FirstInHandler extends ChannelHandlerAdapter {
    private static Logger logger = Logger.getLogger(FirstInHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("start----->>split-Http-Head-Body");
        FullHttpRequest request=(FullHttpRequest)msg;
        logger.info("---------requestMethod----------->>>>>>>>"+request.method());
        ByteBuf buf=request.content();
        String content=buf.toString(CharsetUtil.UTF_8);

        if (content==""){
            logger.info("------->>recMsg---->>body为空....");
        }else {
            logger.info("---->>recMsg"+content);
        }
        ctx.fireChannelRead(content);
    }
}
