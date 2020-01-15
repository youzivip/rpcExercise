package provider;

import handler.DecodeHandler;
import handler.RpcEncoder;
import handler.provider.ProviderHandler;
import handler.register.RegisterServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import register.Server;
import request.Request;
import request.Response;

/**
 * provider 需要向外暴露服务
 * 暴露服务主要做什么：
 *    1 起服务的时候需要上
 *    1 需要有一个可以连接的server
 *    2 需要知道当前所有提供的接口
 *    3 根据consumer请求的接口名进行返回 鉴权
 *    4 接口调用完进行响应
 */
public class Provider extends Server {

    public static void main(String[] args) {
        Provider p = new Provider();
        p.export();
    }

    volatile boolean hasRegiter = false;

    public void register(){
        //todo 也可以在注册完成之后起服务

    }

    /**
     * 向外暴露接口
     */
    public void export(){
        if(!hasRegiter){
            register(); //向服务端注册
        }
        // 起服务 接收consumer的请求
        try {
            super.startServer("127.0.0.1",12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void addChildHandler(ServerBootstrap server) {
        server.childHandler(
                new ChannelInitializer<SocketChannel>() {
                    /**
                     * 增加处理的handler 1 通用处理，
                     */
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //    ByteBuf delimiter = Unpooled.copiedBuffer("\r\n".getBytes());
                        socketChannel.pipeline()
//                                        .addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
//                               .addFirst(new DelimiterBasedFrameDecoder(8192, delimiter))
                                .addLast(new DecodeHandler(Request.class))
                                // 还可以加上校验的使用
                                .addLast(new ProviderHandler())
                                .addLast(new RpcEncoder(Response.class))
                        ;

                    }
                });
    }
}
