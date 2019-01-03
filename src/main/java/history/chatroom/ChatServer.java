package history.chatroom;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Created by  qiao
 * @date 18-3-27 下午7:07
 */

public class ChatServer {
    private int port=8080 ;

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {
       /*创建俩个线程组*/
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChatServerInitialize())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("服务端已经启动....");
            future.channel().closeFuture().sync();
            System.out.println("关闭服务器所有的通道");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new ChatServer(8080).start();
    }
}
