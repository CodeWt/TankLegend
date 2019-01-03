package history.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Created by  qiao
 * @date 18-3-27 下午6:21
 */

public class NettyServer {
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private int port=8080;
//    private String host;
    public void start(){
        EventLoopGroup bossgroup = new NioEventLoopGroup();
        EventLoopGroup workgroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossgroup, workgroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new Serverhandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = server.bind(port).sync();

            future.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            bossgroup.shutdownGracefully();
            workgroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyServer().start();
    }

}
