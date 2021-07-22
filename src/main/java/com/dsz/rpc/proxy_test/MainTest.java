package com.dsz.rpc.proxy_test;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 */
public class MainTest {

    public static void main(String[] args) {
        ProxyTestService proxyInstance = ProxyUtil.getProxyInstance(ProxyTestService.class);
        String s = proxyInstance.sayHello("888888");
        System.out.println(s);
    }

}

