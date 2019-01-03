package history.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import utils.FullHttpRequestUtils;

import java.io.UnsupportedEncodingException;

/**
 * @Created by  qiao
 * @date 18-4-18 下午2:54
 */
public class HttpContentHandler extends ChannelHandlerAdapter {//拆的剩包体
    private static Logger logger = Logger.getLogger(HttpContentHandler.class.getName());

    //    private final String uri ;


    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {

        JSONObject jsonObject = FullHttpRequestUtils.ContentToJson(msg);
        System.out.println(FullHttpRequestUtils.getUri(msg));

        ctx.fireChannelRead(jsonObject);


        //        FullHttpRequest request = (FullHttpRequest)msg;
        //        System.out.println(request.uri());
        //        /*获取请求头*/
        //        HttpHeaders head = request.headers();
        //        head.get("Cookie");
        //        System.out.println("~~~~~~~~~~~~~~~~~~~~~");
        //        System.out.println( head.get("."));
        //        System.out.println("~~~~~~~~~~~~~~~~~~~~~");
        ///*获取请求内容*/
        //        ByteBuf body = request.content();
        //        byte[] bytes = new byte[body.readableBytes()];
        //        body.readBytes(bytes);
        //        String content = new String(bytes,"UTF-8");
        //        System.out.println("================================1");
        //        System.out.println(content);
        //        JSONObject jsonObject = JSON.parseObject(content);
        //-----------------------------
        //        System.out.println("================================");
        ////        System.out.println("session"+jsonObject.get(JsonKeyword.SESSION));
        //        System.out.println("--------------------------------");
        //        System.out.println(request.content().toString());
        //        System.out.println("================================");
        //        ByteBuf body1 = (ByteBuf) request;
        //        byte[] bytes1 = new byte[body.readableBytes()];
        //        body1.readBytes(bytes1);
        //        String requests = new String(bytes1, "utf-8");
        //        JSONObject jsonObject1 = JSON.parseObject(String.valueOf(requests));
        //        System.out.println("==================================requests");
        //        System.out.println(requests.toString());
        //        System.out.println("==================================requests");
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
