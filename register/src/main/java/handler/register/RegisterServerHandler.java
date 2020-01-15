package handler.register;

import com.alibaba.fastjson.JSONObject;
import domin.RequestTypeEnum;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import request.Request;
import request.Response;

public class RegisterServerHandler extends SimpleChannelInboundHandler<Request> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        System.out.println("json-->"+JSONObject.toJSONString(request));
        if(request.getType() == RequestTypeEnum.PROVIDER.getType()) {
            // todo 加上zk注册 ip地址怎么获取
            System.out.println("模拟Zk注册～～");
        }
        Response response = new Response();
        ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                System.out.println("Send response for request " +JSONObject.toJSONString(request));
            }
        });
    }
}
