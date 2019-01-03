package server;


import config.Configurator;
import config.Log4JConfig;
import dao.UserPlayInfo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import utils.redis.TankJedisPool;

import java.util.concurrent.*;


public class PSServer {
    private int port;
    private String host;
    private TankJedisPool tankJedisPool;
    public static UserPlayInfo userPlayInfo =new UserPlayInfo();
    public static CountDownLatch countDownLatch = new CountDownLatch(3);
    public static ExecutorService executor = Executors.newFixedThreadPool(3);


    private PSServer() {
    }

    private void init() throws Exception {
        Configurator.init();
        Log4JConfig.load();
        Thread listener = new Thread(new ConsoleListener());
        listener.start();
        this.tankJedisPool =new TankJedisPool(Configurator.getJPWorkMaxNum(),Configurator.getJPWorkMinNum(),
                Configurator.getJPHost(),Configurator.getJPPort());
    }

    public static PSServer newInstance() throws Exception {
        PSServer psServer = new PSServer();
        psServer.init();
        return psServer;
    }

    public PSServer bind(String host,int port){
        this.host = host;
        this.port = port;
        return this;
    }

    public void run(){
        EventLoopGroup bossgroup = new NioEventLoopGroup();

        EventLoopGroup workgroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();

            server.group(bossgroup, workgroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.TCP_NODELAY, true)

                    .childHandler(new ChannelInitializerImp(tankJedisPool));

            ChannelFuture future = server.bind(host,port).sync();

            future.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            bossgroup.shutdownGracefully();
            workgroup.shutdownGracefully();
        }
    }

    public void close(){
    }

    public UserPlayInfo getUserPlayInfo() {
        return userPlayInfo;
    }

    public void setUserPlayInfo(UserPlayInfo userPlayInfo) {
        this.userPlayInfo = userPlayInfo;
    }
}

