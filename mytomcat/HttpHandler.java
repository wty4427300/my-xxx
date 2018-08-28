package mytomcat;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.Date;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.stomp.StompHeaders.CONTENT_TYPE;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        System.out.println("当前请求的路径是："+fullHttpRequest.getUri());
        String path=fullHttpRequest.getUri().toString();
        String []strs=null;
        String aaa[]=null;
        Date date = new Date();
        try{
           aaa=path.split("/");
           System.out.println(aaa[1]);

           if(aaa[0]!=null){
               strs=aaa[1].split("%=");
               System.out.println("分离的请求是："+strs[0]);
               System.out.println("分离的参数是："+strs[1]);
               System.out.println("分离的参数是："+strs[2]);
               int abc=Integer.parseInt(strs[2]);
               System.out.println(abc);
               FullHttpResponse fullHttpResponse=null;
               Class c=Class.forName("mytomcat."+strs[0]);
               System.out.println(c);
               Servlet123 test=(Servlet123) c.newInstance();
               System.out.println(test);
               myHttpRequest a123=new myHttpRequest();
               myResponse a321=new myResponse();
               System.out.println(a123);
               String rsp=test.doGet(a123,a321);
               System.out.println(rsp);
               fullHttpResponse=new DefaultFullHttpResponse(HTTP_1_1,
                       OK, Unpooled.wrappedBuffer(rsp.getBytes("UTF-8")));
               fullHttpResponse.headers().set(CONTENT_LENGTH, fullHttpResponse.content().readableBytes());
               ctx.writeAndFlush(fullHttpResponse);
           } else {
               System.out.println("分离的请求是："+strs[0]);
               String rsp = "</br></br></br></br><center><h1><p>ok!!!!!</p>2333333333!!!!!!!!"+date.toString()+"</h1></center>";
               //创建response进行响应（这里顺带填充了我需要输出的内容）
               FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                       OK, Unpooled.wrappedBuffer(rsp.getBytes("UTF-8")));
               response.headers().set(CONTENT_TYPE, "text/html");
               response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
               // 这里一个定要flush()，不然浏览器那边不认为服务器已经完成了一次完整的响应
               ctx.writeAndFlush(response);
           }
        }catch (Exception e){
            System.out.println("请求没参数"+e);
            String rsp = "</br></br></br></br><center><h1><p>ok!!!!!</p>2333333333!!!!!!!!"+date.toString()+"</h1></center>";
            //创建response进行响应（这里顺带填充了我需要输出的内容）
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                    OK, Unpooled.wrappedBuffer(rsp.getBytes("UTF-8")));
            response.headers().set(CONTENT_TYPE, "text/html");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            // 这里一个定要flush()，不然浏览器那边不认为服务器已经完成了一次完整的响应
            ctx.writeAndFlush(response);
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Http请求注册时触发");
        super.channelRegistered(ctx);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("扑获异常时触发");
        super.exceptionCaught(ctx, cause);
    }
}
