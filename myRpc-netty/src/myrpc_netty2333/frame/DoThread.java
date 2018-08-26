package myrpc_netty2333.frame;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class DoThread extends SimpleChannelInboundHandler<String> {
    private IDoSomething iDosomething=null;
    DoThread(IDoSomething iDosomething){
        this.iDosomething=iDosomething;
    }
    String []line=null;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("client response :"+msg);
        line=msg.split("\\|");
        System.out.println(line[1]+line[2]+line[3]);
        String classname=line[0];
        String methodname=line[1];
        System.out.println(methodname);
        String res="";
        Method[] m=iDosomething.getClass().getDeclaredMethods();
        for (int i = 0; i < m.length; i++) {
            String name = m[i].getName();
            System.out.println(m[i]);
            if (name.equals(methodname)) {
                res = iDosomething.doSomething(m[i], msg);
                System.out.println(res);
            }
        }
        System.out.println("拖延时间啊！！！！！１");
        System.out.println("拖延时间啊！！！！！２");
        System.out.println("拖延时间啊！！！！！３");
        System.out.println("拖延时间啊！！！！！４");
        ctx.writeAndFlush("\n");
        ctx.writeAndFlush(res);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("(服务器)channelActive：没错傻叉你连上我了");
    }

    //客户端断开
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("(服务器)channelInactive：与客户端断开");
    }




    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 此处可能因为客户端机器突发重启
        // 也可能是客户端链接闲置时间超时，后面的ReadTimeoutHandler抛出来的异常
        // 也可能是消息协议错误，序列化异常
        // etc.
        // 不管它，链接统统关闭，反正客户端具备重连机制
        System.out.println("connection error");
        cause.printStackTrace();
        ctx.close();
    }

}


