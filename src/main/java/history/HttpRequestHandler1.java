package history;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.log4j.Logger;
import service.LoginService;
import service.RegisterService;
import utils.redis.TankJedisPool;

/**
 * @Created by  qiao
 * @date 18-4-7 下午5:38
 */

public class HttpRequestHandler1 extends ChannelHandlerAdapter {
    private static Logger logger = Logger.getLogger(HttpRequestHandler1.class.getName());
    private TankJedisPool tankJedisPool;
    //    private final String URI ;
    public HttpRequestHandler1(TankJedisPool tankJedisPool) {
        this.tankJedisPool=tankJedisPool;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request =(FullHttpRequest)msg;
        String requestUri =request.uri();
        /*通过uri获取请求所对应的类名*/
        String UriClass = requestUri.substring(1).trim()+"Service";

        ByteBuf body = request.content();
        byte[] bytes = new byte[body.readableBytes()];
        body.readBytes(bytes);
        String content = new String(bytes,"UTF-8");
        logger.info("------------ Print content start------------");
//        System.out.println("==============content");
        System.out.println(content);
        logger.info("------------ Print content end  ------------");
//        System.out.println("==============content");
        JSONObject jsonObject = JSON.parseObject(content);

        String path ="service."+UriClass;
        logger.info("------------进入LoginService------------");
//        Class clazz = Class.forName(path);
//        System.out.println("进入LoginService");
//        Field redisField = clazz.getDeclaredField("tankJedisPool");
//        redisField.setAccessible(true);
//        redisField.set(clazz,tankJedisPool);
//        Method redisPool = clazz.getDeclaredMethod("setTankJedisPool", TankJedisPool.class);
//        Object object1=redisPool.invoke(clazz.newInstance(), tankJedisPool);
//        System.out.println("~~" + object1 + "~~");
//        Method channelRead = clazz.getDeclaredMethod("channelRead", JSONObject.class);
//        Object object=channelRead.invoke(clazz.newInstance(),jsonObject);
        System.out.println("------------进入RegisterService中设置tankJedisPool------------");
        /*执行对象的函数.*/
        Reflection r =new Reflection();
        Object result=null;
        Object tmp=r.newInstance(path, getTankJedisPool());
        if(UriClass.equals("LoginService")){
            LoginService loginService =(LoginService)tmp;
            result=r.invokeMethod(loginService, "channelRead",jsonObject);
            System.out.println("=====进入 loginService====");
        }else if(UriClass.equals("RegisterService")){
            RegisterService registerService =(RegisterService)tmp;
            result=r.invokeMethod(registerService,"channelRead",jsonObject);
            System.out.println("=====进入 RegisterService====");
        }else {
            result = "正在处理中!!!";
        }

        logger.info("------------return from LoginService to HttpRequestHandle1------------");
//        System.out.println("return from LoginService to HttpRequestHandle1!!!");
        ctx.writeAndFlush(result);
        System.out.println("====== 结束 HttpRequestHandler1======");
    }

    public TankJedisPool getTankJedisPool() {
        return tankJedisPool;
    }
}
