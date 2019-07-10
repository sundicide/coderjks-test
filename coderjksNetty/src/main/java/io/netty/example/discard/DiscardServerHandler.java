package io.netty.example.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * client로 부터 새로운 데이터를 수신했을 때 호출된다.
     * 이번 예제에서 받은 메세지의 타입은 ByteBuf이다.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Discard the received data silently.
        /**
         * 여기에선 받은 메세지를 무시해야 한다.
         * ByteBuf는 release() 메소드를 통해 명시적으로 release 되야 하는 reference-counted object이다.
         * Handler에게 전달된 reference-counted object는 반드시 release를 해줘야 한다.
         */
        ((ByteBuf) msg).release(); //
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
