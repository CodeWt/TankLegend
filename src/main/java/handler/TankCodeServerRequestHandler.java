package handler;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import config.Configurator;
import dao.JsonKeyword;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import utils.FullHttpRequestUtils;
import utils.redis.TankJedisPool;
import java.io.*;
import java.net.*;

/**
 * @Created by  qiao
 * @date 18-4-24 下午6:41
 */

public class TankCodeServerRequestHandler extends ChannelHandlerAdapter {
    private static Logger logger = Logger.getLogger(TankCodeServerRequestHandler.class.getName());
    private TankJedisPool tankJedisPool;

    public TankCodeServerRequestHandler(TankJedisPool tankJedisPool) {
        this.tankJedisPool=tankJedisPool;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws JSONException, UnsupportedEncodingException {
        String uri= FullHttpRequestUtils.getUri(msg);
        if (uri.equals("TankCodeServerRequest")) {
            JSONObject body=FullHttpRequestUtils.ContentToJson(msg);
            String username = body.getString(JsonKeyword.USERNAME);

            /*接受请求的指令 并转化为指定格式的字符串  存到redis中（断线重连时或许会使用到）*/
            /*建立socket连接，连接python服务器*/
            /*发送指定字符串，并返回指定的字符串*/
            String  result =jsonPost("http://127.0.0.1:8888/playingArgs", body);
            logger.info("[TankCodeServerRequest]: "+result+".  (success)  ");
            /*解析返回的字符串*/

            ctx.writeAndFlush(result).addListener(ChannelFutureListener.CLOSE);

        } else {
            ctx.fireChannelRead(msg);
        }

    }

    private String getConnectPython(Object msg) {
        try {
            Socket socket = new Socket(Configurator.getTankCodeHandleServerHost(), Configurator.getTankCodeHandleServerPost());
            System.out.println("================");
            System.out.println(socket.getLocalPort());
            System.out.println("================");
            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.println(msg);
            ps.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "aaa";
    }

    public static String jsonPost(String strURL,JSONObject body) {
        try {
            if(strURL.startsWith("\uFEFF")){
//                strURL = strURL.substring(1);
                strURL = strURL.replace("\uFEFF", "");
            }
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
//            out.append(JSONUtil.object2JsonString(params));
            String jsonArray = body.toJSONString();
            out.append(jsonArray);
            out.flush();
            out.close();

            int code = connection.getResponseCode();
            InputStream is = null;
            if (code == 200) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }

            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                return result;
            }

        } catch (IOException e) {
            logger.error("Exception occur when send http post request!", e);
        }
        return "error"; // 自定义错误信息
    }
}
