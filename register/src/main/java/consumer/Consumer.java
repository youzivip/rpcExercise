package consumer;

import client.Client;
import com.alibaba.fastjson.JSONObject;
import handler.client.SimpleClientHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import request.Request;
import service.HelloService;
import sun.jvm.hotspot.jdi.InterfaceTypeImpl;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *  1 需要获取存活的channel,进行负载均衡
 *  2 需要生成代理
 */
public class Consumer {

    static Object createProxy(Class clz){
        //  1 要给接口生成代理类
        //  2 代理类需要发送请求  生成request
        if(! clz.isInterface()) return null;
        Class clzs[] = {clz};
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), clzs,
                new InvocationHandler() {


                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        Client client = new Client();
                        client.startConnection("127.0.0.1",12000);
                        Thread.sleep(1000l);
                        Request request = new Request();
                        request.setUrl(clz.getName());
                        request.setMethod(method.getName());
                        request.setParam(args);
                        client.chooseHandler().sendRequest(request);
//        for (int i = 0; i <5 ; i++){
//            request.setParam("Jay"+i+"   ");
//            client.chooseHandler().sendRequest(request);
//        }

                        Thread.sleep(5000l);

                        return null;
                    }
                });
        return proxy;

    }

    //设定有client 使用netty客户端，发送request
    public static void main(String[] args) throws InterruptedException {

        HelloService helloService = (HelloService)createProxy(HelloService.class) ;
        helloService.sayHi("1");
//        SendRequest();


    }

    private static void SendRequest() throws InterruptedException {
        Client client = new Client();
        client.startConnection("127.0.0.1",12000);
        Thread.sleep(1000l);
        Request request = new Request();
        request.setType(1);
        request.setUrl("service.HelloService");
        request.setMethod("sayHi");
        request.setParam("Jay");
        client.chooseHandler().sendRequest(request);
//        for (int i = 0; i <5 ; i++){
//            request.setParam("Jay"+i+"   ");
//            client.chooseHandler().sendRequest(request);
//        }

        Thread.sleep(5000l);
    }

}
