package handler;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.*;
import org.apache.log4j.Logger;
import java.io.UnsupportedEncodingException;

import static io.netty.handler.codec.http.HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN;
import static io.netty.handler.codec.stomp.StompHeaders.*;

public class HttpHeadHandler extends ChannelHandlerAdapter {
    private static Logger logger = Logger.getLogger(HttpHeadHandler.class.getName());

        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws UnsupportedEncodingException {
        String body = (String)msg;
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set(CONTENT_TYPE,"text/html;charset=UTF-8");
        response.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN,"*");/*解决服务器问题*/
        response.headers().set(CONTENT_LENGTH,body.getBytes("UTF-8").length+"");
        response.headers().set(MESSAGE_ID,"Cookie-123456");

//        response.headers().set()
//        response.headers().set(SESSION,"11");
//        response.();
        response.content().writeBytes(body.getBytes("UTF-8"));
//        response.content().
        String a=response.headers().toString();
        String b =response.content().toString();
        logger.info("=====>Print response.headers<=====");
        System.out.println(a);
        logger.info("=====>Print response.content<=====");
        System.out.println("this is :"+b);
        logger.info("=====>Print HttpHeadHandler Left<=====");
        ctx.write(response);
    }

}
