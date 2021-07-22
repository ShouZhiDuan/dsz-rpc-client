package com.dsz.rpc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.IOException;
/**
 * 客户端启动入口
 */
public class App {
    public static void main( String[] args ) throws InterruptedException, IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        //调用代理类
        RpcProxyClient rpcProxyClient = context.getBean(RpcProxyClient.class);
        IHelloService iHelloService = rpcProxyClient.clientProxy(IHelloService.class,"v2.0");
        for(int i = 0; i < 100; i++) {
            Thread.sleep(2000);
            iHelloService.sayHello(1.0);
        }
        System.in.read();
    }
}
