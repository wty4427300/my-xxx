package myrpc_netty2333.frame;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Send {
    private int port;
    private String host;
    private EventLoopGroup group = null;
    private  String aaa;


    public Send(int port, String host) {
        this.port = port;
        this.host = host;
    }
    public  String something(String str){
        this.aaa=str;
        return "TRUE";
    }
    public void start(){
        group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast("StringDecoder",  new StringDecoder());
                            ch.pipeline().addLast("StringEncoder",  new StringEncoder());
                            ch.pipeline().addLast("Handler",  new Handler(aaa));
                        }
                    });

            // 发起异步连接操作
            ChannelFuture f = b.connect(host, port).sync();

            // 当代客户端链路关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }


    public class Handler extends SimpleChannelInboundHandler<String> {
        private String bbb;
        public Handler(String str){
            this.bbb=str;
        }
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println("server response ： "+"\n"+msg);
            ctx.close();
        }
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //给服务器发消息
            ctx.channel().writeAndFlush(bbb);
            System.out.println("(客户端)channelActive：连上服务器了,没毛病");
        }

        //与服务器断开连接
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("(客户端)channelInactive：煞笔滚！");
        }
        //异常
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            //关闭管道
            ctx.close();
            //打印异常信息
            cause.printStackTrace();
        }

    }
}
