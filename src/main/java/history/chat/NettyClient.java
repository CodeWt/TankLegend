package history.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Created by  qiao
 * @date 18-3-27 下午6:40
 */

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup workgroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workgroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ClientHander());
                    }
                });

        ChannelFuture future = bootstrap.connect("127.0.0.1", 8080);
        future.channel().writeAndFlush(Unpooled.copiedBuffer("你好! ".getBytes()));
        future.channel().closeFuture().sync();
        workgroup.shutdownGracefully();
        System.out.println("end......");

    }

}
