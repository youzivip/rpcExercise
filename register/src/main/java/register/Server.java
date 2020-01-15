package register;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public abstract class  Server {

    private ServerBootstrap getB (){
        ServerBootstrap server = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        server.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128) // 设置TCP缓冲区
//                .option(ChannelOption.SO_SNDBUF, 32 * 1024) // 设置发送缓冲大小
//                .option(ChannelOption.SO_RCVBUF, 32 * 1024) // 这是接收缓冲大小
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        return server;
    }

    public void startServer(String host,int port) throws InterruptedException {
        ServerBootstrap server = getB();
        addChildHandler(server);
        String serverAddress = host+":"+port;
        ChannelFuture future = server.bind( port).sync();
        ChannelFuture channelFuture = future.addListener(future1 -> {
                if (future.isSuccess()) {
                    System.out.println("Server have success bind to " + serverAddress);
                } else {
                    System.out.println("Server fail bind to " + serverAddress);
                    throw new Exception("Server start fail !", future.cause());
                }
            });
    }

    //todo 可以做成组合
    protected abstract void addChildHandler(ServerBootstrap server) ;
}
