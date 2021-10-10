package org.example.week02.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MycilentHand extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            byte[] buffer = new byte[buf.readableBytes()];
            buf.readBytes(buffer);
            String message = new String(buffer,"utf-8");
        System.out.println("响应的内容"+message);
    }

}
