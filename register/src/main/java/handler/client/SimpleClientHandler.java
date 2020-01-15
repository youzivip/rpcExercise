package handler.client;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import request.Request;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {


    private volatile Channel channel;


    /**
     * 读取客户端通道的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(JSONObject.toJSONString(msg));
    }

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("我可以发送内容了～～～");
   //     channel = ctx.channel();
//        //向服务端发送内容
//        Request request = new Request();
//        request.setType(1);
//        request.setUrl("service.HelloService");
//        request.setMethod("sayHi");
//        request.setParam("Jay");
//        //  BufferedReader reader = new BufferedReader();
////        ByteBuf buf = Unpooled.buffer(1024*1024);
//        //发送数据
//        ctx.channel().writeAndFlush(request);
    }

    public  void sendRequest(Request request){
        channel.writeAndFlush(JSONObject.toJSONString(request).getBytes());
    }


}
