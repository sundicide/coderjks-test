package io.netty.example.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * ChannelActive는 connection이 생성되고 traffic을 생성할 준비가 됐을 때 사용된다.
     * @param ctx
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        /**
         * 새로운 메세지를 보내기 위해서는 메세지를 포함할 new buffer에 allocate 해야 한다.
         * 우리는 32-bit integer를 write 할 것이므로 ByteBuf에는 최소한 4bytes가 필요하다.
         * ByteBufAllocator를 ChannelHandlerContext.alloc()을 통해서 get하고 new buffer를 allocate 한다.
         */
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        /**
         * NIO로 메세지를 보내기 전에 java.nio.ByteBuffer.flip()을 사용해야 하는 것이 아닌가?
         * ByteBuf는 해당 메서드가 없으며 단지 두 개의 pointers만 있다. read를 위한 것 하나, write를 위한 것 하나.
         * ByteBuf에 무언가를 쓸때 writeIndex는 증가하지만 readIndex는 증가하지 않는다.
         * readerIndex와 writerIndex는 메세지의 시작과 종료를 나타낸다.
         *
         * 이와 반대로 NIO buffer는 flip() 없이는 메세지의 시작과 끝이 어딘지 정확하게 알 수 없다.
         * 만약 flip the buffer를 잊어버린다면 잘못된 데이터가 보내질 것이다.
         * 하지만 이와같은 오류는 Netty에서 발생하지 않는다. flip이 필요 없다.
         *
         * 또 다른 특이점으로는 ChannelHandlerContext.write() (and writeAndFlush()) 메소드가 ChannelFuture를 return 한다는 것이다.
         * ChannelFuture는 아직 발생하지 않은 I/O opertaion을 의미한다.
         * 이 말은 any requested operation 은 아직 수행되지 않는다는 것이다. Netty는 모두 비동기적으로 동작하기 때문에.
         * 예를 들어 아래의 코드는 다음과 같은 코드는 connection을 닫아버린다. 메세지가 보내지기 전에
         */
        final ChannelFuture f = ctx.writeAndFlush(time);
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                assert f == future;
                ctx.close();
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
