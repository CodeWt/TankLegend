package server;


import dao.UserPlayInfo;
import handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import utils.redis.TankJedisPool;


public class ChannelInitializerImp extends ChannelInitializer<NioSocketChannel> {
    private UserPlayInfo userPlayInfo;
    private TankJedisPool tankJedisPool;

    public ChannelInitializerImp(TankJedisPool tankJedisPool)
    {
        this.tankJedisPool=tankJedisPool;
    }

    @Override
    protected void initChannel(NioSocketChannel channel) throws Exception {
        channel.pipeline().addLast(new HttpRequestDecoder());//inbound
        channel.pipeline().addLast(new HttpObjectAggregator(65536));//inbound
        channel.pipeline().addLast(new HttpResponseEncoder());//outbound
        channel.pipeline().addLast(new HttpHeadHandler());//outbound

        channel.pipeline().addLast(new LoginHandler(tankJedisPool));//inbound outbound
        channel.pipeline().addLast(new RegisterHandler(tankJedisPool));
        channel.pipeline().addLast(new TankHandler(tankJedisPool));//inbound outbound
        channel.pipeline().addLast(new MapHandler(tankJedisPool));//inbound outbound
        channel.pipeline().addLast(new TankCodeHandler(tankJedisPool));//inbound outbound
        channel.pipeline().addLast(new TankCodeServerRequestHandler(tankJedisPool));//inbound outbound

        channel.pipeline().addLast(new TradeUserInfoGetHandler(tankJedisPool));//inbound outbound
        channel.pipeline().addLast(new TradeUserInfoPutHandler(tankJedisPool));//inbound outbound
        channel.pipeline().addLast(new MatchHandler(tankJedisPool));

        channel.pipeline().addLast(new FirstInHandler());//inbound outbound
        channel.pipeline().addLast(new GetGarageHandler(tankJedisPool));//inbound outbound
//        channel.pipeline().addLast(new OutPutHandler());//inbound outbound


//        channel.pipeline().addLast(new HttpContentHandler());//inbound  HttpRequestHandler1
//        channel.pipeline().addLast(new HttpRequestHandler2(tankJedisPool));
//        channel.pipeline().addLast(new HttpRequestHandler1(tankJedisPool));

    }
}
