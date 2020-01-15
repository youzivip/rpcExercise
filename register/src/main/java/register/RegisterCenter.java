package register;

import handler.DecodeHandler;
import handler.RpcEncoder;
import handler.register.RegisterServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import request.Request;
import request.Response;

public class RegisterCenter  extends Server{
    public static void main(String[] args) throws InterruptedException {
        // 起一个接受链接的服务，链接zk

        // 如果请求的是provider 则查找是否有该接口的节点，如果有的话，增加IP，没有的话，增加节点
        // 如果请求的是Consumer 节点，consumer应该是直接获取list请求
        RegisterCenter registerCenter = new RegisterCenter();
        registerCenter.startServer("127.0.0.1",8080);
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
                                .addLast(new RegisterServerHandler())
                                .addLast(new RpcEncoder(Response.class))
                        ;

                    }
                });
    }
}
