package io.netty.example.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * client로 부터 새로운 데이터를 수신했을 때 호출된다.
     * 이번 예제에서 받은 메세지의 타입은 ByteBuf이다.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        /**
         * ChannelHandlerContext 객체는 다양한 I/O events와 operations을 실행할 수 있는 기능을 제공한다.
         * 여기에선 write(Object)를 사용했는데 이는 received message를 write한다는 의미이다.
         * 여기에선 DiscadServerHandler와 다르게 release를 안했다는 것을 주의해라.
         * written out to the wire 된다면 Netty가 알아서 release를 해주기 때문이다.
         */
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        /**
         * write가 message를 written out to the wire 해주진 않는다.
         * write는 단지 내부적으로 buffered 할 뿐이고 flush를 하고 나서야 flushed out 된다.
         * ctx.writeAndFlush(msg)로 이 두 문장을 한 번에 해결할 수 있다.
         */
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
