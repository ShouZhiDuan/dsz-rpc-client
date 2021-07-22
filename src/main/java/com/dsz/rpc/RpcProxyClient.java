package com.dsz.rpc;

import com.dsz.rpc.discovery.IServiceDiscovery;
import com.dsz.rpc.discovery.ServiceDiscoveryWithZk;
import java.lang.reflect.Proxy;

public class RpcProxyClient {

    private IServiceDiscovery serviceDiscovery = new ServiceDiscoveryWithZk();

    public <T> T clientProxy(final Class<T> interfaceCls, String version){
        return (T)Proxy.newProxyInstance(
                interfaceCls.getClassLoader(), //被代理类加载器
                new Class<?>[]{interfaceCls},  //被代理类接口
                new RemoteInvocationHandler(serviceDiscovery,version) //实现类
                );
    }
}
