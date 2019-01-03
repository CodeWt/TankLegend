package history.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * @Created by  qiao
 * @date 18-4-2 下午2:02
 */

public class WebChatServerInitialize extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline =socketChannel.pipeline();
        /*
        * 利用websocket通信
        *
        * 1.建立wetsocket连接时,客户端浏览器,首先要向服务断发送握手的请求 ,是http中的请求
        * 请求头中有一个头信息是"upgrade:websocket" 通过该协议来向服务端表明写以为websocket协议
        * 2. 服务端协议接受到之后,会生成应答信息返回给客户端.  这个应答信息是http协议的response响应,
        * 3.客户端收到信息后,组websocket的连接建立完毕.后续的通讯就不在是http协议了
        *  而是websocket协议了。
        *
        *  最终服务器端和客户气端就是双工模式.可以互相同时传信息.
        * */
        pipeline.addLast(new HttpServerCodec())/*将请求和应答消息编码解码为http消息.*/
                .addLast(new HttpObjectAggregator(64 * 1024))
                .addLast(new ChunkedWriteHandler(65535))/*向客户发送html5的页面文件.*/
                .addLast(new HttpRequestHandler("/chat"))/*in  区分http请求,读取html页面并写回客户端浏览器.*/
                .addLast(new WebSocketServerProtocolHandler("/chat"))/*out  数据怔和控制帧  请求分为打开和请求两种.*/
                .addLast(new TextWebSocketFrameHandler());/*out     */
    }
}












