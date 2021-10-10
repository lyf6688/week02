package org.example.week02.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyNettyHttpClient {
    public static void main(String[] args) {
        sendNettyClient("127.0.0.1",8801);
    }

    public static String sendNettyClient(String host,int port) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
         ChannelFuture mChannelFuture;
        try {

            Bootstrap b = new Bootstrap();
            b.group(workerGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new MyclientHandInit());
            mChannelFuture = b.connect(host, port).sync();
            mChannelFuture.channel().closeFuture().sync();
        }catch (Exception e){

        } finally {
            workerGroup.shutdownGracefully();
        }


        return null;
    }
}
