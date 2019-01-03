import dao.JsonKeyword;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @Created by  qiao
 * @date 18-3-8 下午9:33
 */

public class TestStatic {
    private  int a;
    public static void main(String[] args) throws Exception {

        String value ="aaa";
        String path = "/home/qiao/IdeaProjects/tanklegendnetty/src/test/java";
        Class clazz = Class.forName("log."+value);
        Constructor constructor = clazz.getConstructor();
        Method channelRead = clazz.getDeclaredMethod("toString", Integer.class);
        channelRead.invoke(value, "toString");

    }
}
