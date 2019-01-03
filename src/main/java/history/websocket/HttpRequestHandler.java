package history.websocket;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedNioFile;


import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

import static io.netty.handler.codec.http.HttpVersion.*;


/**
 * 用来区分页面的请求是html页面还是websocket请求.
 * @Created by  qiao
 * @date 18-4-2 下午6:19
 */

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final String chatURI ;/*webSocket 的地址.*/
    private static  File indexFile;

    static {
        try {
        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
            String path = location.toURI() + "index.html";
            path = !path.contains("file:") ? path : path.substring(5);/*去除file:*/
            indexFile = new File(path);
            System.out.println(path);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public HttpRequestHandler(String chatURI) {
        this.chatURI=chatURI;
    }


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        System.out.println(request.uri());

        if(chatURI.equals(request.uri())){
            //用户请求的是websocket地址,那么就不处理,之后将请求转发给管道下一个处理环节
            ctx.fireChannelRead(request.retain());
        }else{
            //那么就读取聊天页面 ,并将页面的内容写给客户端浏览器.
            if(HttpHeaderUtil.is100ContinueExpected(request)){
                FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                        HttpResponseStatus.CONTINUE);
                System.out.println(response.toString()+"=============1");
                ctx.writeAndFlush(response);
            }


            /*设置http响应头.*/
            RandomAccessFile file = new RandomAccessFile(indexFile, "r");
            request.setProtocolVersion(HTTP_1_1);
            HttpResponse response =new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK);/*设置请求是成功的. request.protocolVersion() */
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
            boolean keepAlive = HttpHeaderUtil.isKeepAlive(request);


            if (keepAlive) {
//                String a= String.valueOf(file.length());
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH,String.valueOf(file.length()));
                response.headers().set(HttpHeaderNames.CONNECTION   , HttpHeaderValues.KEEP_ALIVE);
            }
            System.out.println(response+"====================2");
            ctx.write(response);
            System.out.println(file.length());


            /*设置协议文件 并把html文件发出去.*/
            ctx.write(new ChunkedNioFile(file.getChannel()));
            System.out.println("===================ChunkedNioFile");
            /*刷新管道*/
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if (keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
            file.close();
//            future.channel().closeFuture().sync();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
