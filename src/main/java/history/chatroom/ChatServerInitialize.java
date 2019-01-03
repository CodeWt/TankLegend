package history.chatroom;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * @Created by  qiao
 * @date 18-3-27 下午7:21
 */

public class ChatServerInitialize extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        System.out.println("有客户端接入." + channel.remoteAddress());
        ChannelPipeline channelPipeline =channel.pipeline();
        channelPipeline.addLast("frame",new DelimiterBasedFrameDecoder(3092, Delimiters.lineDelimiter()));
        channelPipeline.addLast("decoder",new StringDecoder());
        channelPipeline.addLast("encoder",new StringEncoder());

        channelPipeline.addLast("handler",new ChatServerHandler());


    }

}
