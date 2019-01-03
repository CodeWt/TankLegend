package history.chatroom;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Created by  qiao
 * @date 18-3-27 下午7:29
 */
@ChannelHandler.Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    /*全部的channels*/
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static  int i=0;
    public static  int j=0;
    public static  int z=0;
    /**
     *
     * @param ctx
     * @throws Exception
     * 当有客户端连接时,就把该客户端的通道记录下来,加入队列
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel ch:channels) {
            if (ch != incoming) {
                ch.writeAndFlush("[欢迎:]" + incoming.remoteAddress() + "进入聊天室.\n");
//                j++;
            }else
            {
                ch.writeAndFlush("[我:]" + incoming.remoteAddress() + "来了.\n");
//                z++;
            }

//            i++;
//            System.out.println("================    ==============");
//            System.out.println("=======i  "+i+"=======j  "+j+"=======z  "+z+"  =========  "+channels.size()+"  ==============");
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

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        Channel incoming =ctx.channel();
//        for (Channel ch:channels) {
//            //将客户端发来的书刊居转发给其他人,
//            if (ch != incoming) {
//                channels.writeAndFlush("[用户:" + incoming.remoteAddress()+"说:]"+msg + "\n");
//            }else {
//                channels.writeAndFlush("[我说:]"+msg + "\n");
//            }
//        }
//    }

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
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel incoming =channelHandlerContext.channel();
        for (Channel ch:channels) {
            //将客户端发来的书刊居转发给其他人,
            if (ch!=incoming) {
                ch.writeAndFlush("[用户(messageReceived):" + incoming.remoteAddress()+"说:]"+s + "\n");
                j++;
            } else {
                ch.writeAndFlush("[我说(messageReceived):]"+s + "\n");
                z++;
            }
            i++;
            System.out.println("=======i  "+i+"=======j  "+j+"=======z  "+z+"  =========  "+channels.size()+"  ==============");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming =ctx.channel();
        System.out.println(incoming.remoteAddress()+"通讯异常!");
    }

//    @Override
//    public boolean isSharable() {
//        return super.isSharable();
//    }/**/

    /*
    *
    *
    * */



}
