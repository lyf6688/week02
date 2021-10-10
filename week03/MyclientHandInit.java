package org.example.week02.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

public class MyclientHandInit  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new MycilentHand());
                            // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
        socketChannel.pipeline().addLast(new HttpResponseDecoder());
        socketChannel.pipeline().addLast(new HttpRequestEncoder());
    }
}
