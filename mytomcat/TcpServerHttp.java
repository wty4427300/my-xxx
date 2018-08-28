package mytomcat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class TcpServerHttp {
    //private static final String IP = "127.0.0.1";  //可以自行绑定IP
    private static final int PORT = 2333;  //端口号
    private static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors()*2; //用于分配处理线程的线程组个数
    private static final int BIZTHREADSIZE = 4; //业务线程大小

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
        EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);
        ServerBootstrap b = new ServerBootstrap();

        //绑定服务参数
        b.group(bossGroup,workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                //pipeline.addLast("http-handler",new HttpServerCodec());
                pipeline.addLast("decoder", new HttpRequestDecoder());
                // http服务器端对response编码
                pipeline.addLast("encoder", new HttpResponseEncoder());
                // Http消息组装
                pipeline.addLast("aggregator", new HttpObjectAggregator(65535));
                //最终处理业务的handler
                pipeline.addLast("HttpHandler",new HttpHandler());
            }
        });
        //绑定端口
        b.bind(PORT).sync();
        //b.bind(IP,PORT).sync();
        System.out.println("Http服务已经启动");
    }


    public static void main(String[] args) throws InterruptedException {
        new TcpServerHttp().start();
    }
}