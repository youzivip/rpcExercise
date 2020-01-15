package consumer;

import client.Client;
import handler.client.SimpleClientHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import request.Request;

import java.io.IOException;

public class Consumer {

    //设定有client 使用netty客户端，发送request
    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
        client.startConnection("127.0.0.1",12000);
        Request request = new Request();
        request.setType(1);
        request.setUrl("service.HelloService");
        request.setMethod("sayHi");
        request.setParam("Jay");
        for (int i = 0; i <5 ; i++){
            request.setParam("Jay"+i+"   ");
            client.chooseHandler().sendRequest(request);
        }

    }

}
