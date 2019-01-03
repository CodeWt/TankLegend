package history.chatroom;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Created by  qiao
 * @date 18-3-27 下午8:23
 */

public class ChatClient {
    private String host;
    private int port;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap boot= new Bootstrap();
        try {
        boot.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChatClientInitizalize());

            Channel channel = boot.connect(host,port).sync().channel();

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                channel.writeAndFlush(input.readLine() + "\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new ChatClient("127.0.0.1",8080).start();
    }
}
