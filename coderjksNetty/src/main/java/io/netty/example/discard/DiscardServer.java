package io.netty.example.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Discards any incoming data.
 */
public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        /**
         * NioEventLoopGroup는 I/O 작업을 handle 하는 멀티쓰레드 event loop 이다.
         * Netty는 다양한 종류의 EventLoopGroup을 제공한다.
         * 그 중에서 우리는 서버를 구현한 것이기 때문에 NioEventLoopGroup를 사용한다.
         * 첫 번째인 boss는 incoming connection을 accept한다.
         * 두 번째인 worker는 boss가 accept 후 worker에게 register 한 connection들의 traffic을 다룬다.
         * 얼마나 많은 쓰레드를 사용하고 그들을 Channel에 어떻게 매핑 할지는 생성자로 configure한 EventLoopGroup의 구현에 달려있다.
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            /**
             * ServerBootstrap은 server의 setup 을 도와주는 클래스이다.
             * Channel을 direct로 사용해서 서버를 설정할 수 있지만 대부분의 경우 그럴일은 없다.
             */
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    /**
                     * NioServerSocketChannel은 incoming connection에 대해 Channel을 initiate 한다.
                     */
                    .channel(NioServerSocketChannel.class)
                    /**
                     * 이 부분은 항상 newly accepted Channel에 의해 evaluated된다.
                     * ChannelInitializer는 new Channel을 configure하는데 도움을 주기 위한 목적의 handler이다.
                     * network application에 적용할 DiscardServerHandler와 같은 handler들을 ChannelPiepline에 등록하고자할때 사용한다.
                     * application이 복잡해 질 수록 add 되는 handler들이 많아져 결국엔 새로운 Class를 등록하게 될 것이다.
                     */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    /**
                     * Channel 구현 시 parameter를 전달할 수 있다.
                     * 우리는 TCP/IP server를 구현하기 때문에 소켓 옵션 중 tcpNoDelay와 keepAlive 를 사용할 것이다.
                     * 자세한 사항은 ChannelOption을 참고해라
                     */
                    .option(ChannelOption.SO_BACKLOG, 128)
                    /**
                     * 위에는 option이지만 여기에는 childOption이다.
                     * option은 incoming connection을 받는 NioServerSocketChannel 을 위한 것이다.
                     * childOption은 ServerChannel(여기에선 NioServerSocketChannel)에 의해 accept된 Channel을
                     */
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            /**
             * port를 bind하고 server를 시작한다.
             */
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 9990;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new DiscardServer(port).run();
    }
}
