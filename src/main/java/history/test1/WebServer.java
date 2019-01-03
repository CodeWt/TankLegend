package history.test1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Created by  qiao
 * @date 18-3-28 上午8:55
 */

public class WebServer {
    private int port;

    public WebServer(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup boss =new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebChatServerInitialize())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);


            ChannelFuture future= bootstrap.bind(port).sync();
            System.out.println("[信息消息]:服务器以启动.");
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }



    public static void main(String[] args) {
        new WebServer(8080).start();
    }
}
