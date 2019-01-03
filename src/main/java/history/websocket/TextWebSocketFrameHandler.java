package history.websocket;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Created by  qiao
 * @date 18-4-2 下午6:16
 */

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrameHandler> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TextWebSocketFrameHandler msg) throws Exception {
        Channel incoming =channelHandlerContext.channel();
        for (Channel ch:channels) {
            //将客户端发来的书刊居转发给其他人,
            if (ch!=incoming) {
                ch.writeAndFlush(new TextWebSocketFrame("[用户(messageReceived):" + incoming.remoteAddress()+"说:]"+msg.toString() + "\n"));
            } else {
                ch.writeAndFlush(new TextWebSocketFrame("[我说(messageReceived):]"+msg.toString() + "\n"));
            }
        }
    }

    /**
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel ch:channels) {
            if (ch != incoming) {
                ch.writeAndFlush("[欢迎:]" + incoming.remoteAddress() + "进入聊天室.\n");
            }else
            {
                ch.writeAndFlush("[我:]" + incoming.remoteAddress() + "来了.\n");

            }
        }
        channels.add(incoming);
    }

    /*断开连接*/
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming =ctx.channel();
        for (Channel ch:channels) {
            if (ch != incoming) {
                channels.writeAndFlush("[再见:]" + incoming.remoteAddress() + "离开聊天室.\n");
            }
        }
        channels.remove(incoming);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming =ctx.channel();
        System.out.println("["+incoming.remoteAddress()+"] ,在线中!");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming =ctx.channel();
        System.out.println("["+incoming.remoteAddress()+"] ,离线!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming =ctx.channel();
        System.out.println(incoming.remoteAddress()+"通讯异常!");
    }


}
