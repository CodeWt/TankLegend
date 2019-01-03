package history;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import service.LoginService;
import service.RegisterService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.log4j.Logger;
import utils.redis.TankJedisPool;

/**
 * @Created by  qiao
 * @date 18-4-13 下午5:34
 */

public class HttpRequestHandler2 extends ChannelHandlerAdapter {
    private static Logger logger = Logger.getLogger(HttpRequestHandler2.class.getName());
    private TankJedisPool tankJedisPool;

    public HttpRequestHandler2(TankJedisPool tankJedisPool) {
        this.tankJedisPool=tankJedisPool;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request =(FullHttpRequest)msg;
        String requestUri =request.uri();
        /*通过uri获取请求所对应的类名*/
        String UriClass = requestUri.substring(1).trim()+"Handler";
        ByteBuf body = request.content();
        byte[] bytes = new byte[body.readableBytes()];
        body.readBytes(bytes);
        String content = new String(bytes,"UTF-8");
        logger.info("------------ Print content start------------");
        System.out.println(content);
        logger.info("------------ Print content end  ------------");
        JSONObject jsonObject = JSON.parseObject(content);
        String path ="service."+UriClass;
        System.out.println("path:" +path);
        logger.info("------------进入具体的Service------------");
        /*执行对象的函数.*/
        Reflection.Reflection1 r =new Reflection.Reflection1();
        Object result=null;
        Object tmp=r.newInstance(path, getTankJedisPool());
        if(UriClass.equals("LoginService")){
            LoginService loginService =(LoginService)tmp;
            result=r.invokeMethod(loginService, "channelRead",ctx,jsonObject);
            System.out.println("=====进入 loginService====");
        }else if(UriClass.equals("RegisterService")){
            RegisterService registerService =(RegisterService)tmp;
            result=r.invokeMethod(registerService,"channelRead",ctx,jsonObject);
            System.out.println("=====进入 RegisterService====");
        }else {
            result = "正在处理中!!!";
        }

        logger.info("------------return from LoginService to HttpRequestHandle1------------");

        ctx.writeAndFlush(result);
        System.out.println("====== 结束 HttpRequestHandler1======");
    }

    public TankJedisPool getTankJedisPool() {
        return tankJedisPool;
    }
}
