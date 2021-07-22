package com.dsz.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class RpcNetTransport extends SimpleChannelInboundHandler<Object>  {

    private String serviceAddress;

    public RpcNetTransport(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    private Object result;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o){
        this.result=o;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println("异常：");
        ctx.close();
    }

    public Object send(RpcRequest request){
        NioEventLoopGroup eventLoogGroup = new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(eventLoogGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().
                        addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null))).
                        addLast(new ObjectEncoder()).
                        addLast(RpcNetTransport.this);
            }
        }).option(ChannelOption.TCP_NODELAY,true);
        try {
            String urls[] = serviceAddress.split(":");
            ChannelFuture future = bootstrap.connect(urls[0],Integer.parseInt(urls[1])).sync();
            future.channel().writeAndFlush(request).sync();
            if(request!=null){
                future.channel().closeFuture().sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            eventLoogGroup.shutdownGracefully();
        }
        return result;
    }
}
