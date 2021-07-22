package com.dsz.rpc.proxy_test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 */
public class ProxyUtil {

    public static  <T> T getProxyInstance(Class clazz){
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new InvocationHandler() {
                    @Override
                    public String invoke(Object proxy, Method method, Object[] args){
                        //proxy最终字节码代理类
                        //method被代理的方法
                        //被代理方法的参数
                        String arg = args[0].toString();
                        return "我传的参数是：" + arg;
                    }
                }
        );
    }


}
