package handler.provider;

import com.sun.tools.jdi.InterfaceTypeImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import request.Request;
import request.Response;
import service.HelloService;
import service.impl.HelloServiceImpl;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ProviderHandler extends SimpleChannelInboundHandler<Request> {
    final static ConcurrentMap<String,Object> concurrentMap = new ConcurrentHashMap<>();

    static {
        concurrentMap.put(HelloService.class.getName(),new HelloServiceImpl());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ch, Request request) throws Exception {
        Object param = request.getParam();
        String clzName = request.getUrl();
        Class clz = Class.forName(clzName);   //todo 获取的是接口，如果是Spring ,可以先扫描所有的接口放进去实例
//        Object o = clz.newInstance();
        Object o = concurrentMap.get(clzName);
        Method m = clz.getMethod(request.getMethod(),request.getParam().getClass());
        Object result = m.invoke(o,param);
        Response response = new Response();
        response.setData(result);
        ch.channel().writeAndFlush(response);

    }
}
