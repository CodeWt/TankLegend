package handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;

import static io.netty.handler.codec.http.HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.stomp.StompHeaders.CONTENT_TYPE;

public class OutPutHandler extends ChannelHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        String responseMessage = (String)msg;
        System.out.println("out--------->>>>>>>>>>>test"+responseMessage);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                OK, Unpooled.wrappedBuffer(responseMessage.getBytes("UTF-8")));
        response.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN,"*");
        response.headers().set(CONTENT_TYPE, "html/text;charset=utf-8");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        //response.headers().set(CONTENT_TRANSFER_ENCODING,"chunked");
        //response.headers().set(TRANSFER_ENCODING,"chunked");
        ctx.writeAndFlush(response);
    }
}
