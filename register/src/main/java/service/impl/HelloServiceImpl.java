package service.impl;

import service.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String name)  {
        System.out.println(name+"hi");
        try {
            Thread.sleep(200); //模拟逻辑处理
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return name+",hi!";
    }
}
