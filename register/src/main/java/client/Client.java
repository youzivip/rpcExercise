package client;

import handler.DecodeHandler;
import handler.RpcEncoder;
import handler.client.SimpleClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import request.Request;
import request.Response;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {

    List<SimpleClientHandler> handlers = new CopyOnWriteArrayList<>();

     public  void startConnection(String host,int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        SimpleClientHandler handler = new SimpleClientHandler();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline()
                                .addLast(new DecodeHandler(Response.class))
                        .addLast(new RpcEncoder(Request.class))
                                .addLast(handler)
               //         .addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
                        //.addLast(new SimpleClientHandler())
                        ;
                    }
                });
        try {
            //连接服务
            ChannelFuture future =  b.connect(host, port).sync();
            future.addListener(f->{
                System.out.println("连接建立了"+f.get());
                handlers.add(handler);
            });
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
     }


    public SimpleClientHandler chooseHandler(){
        return handlers.get(0);
    }



}
