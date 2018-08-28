package mytomcat;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.stomp.StompHeaders.CONTENT_TYPE;

public class myHttpServlet {
    private  myHttpServlet myhttpservlet=null;
    public myHttpServlet() {
        myhttpservlet=new myHttpServlet();
    }

    public void doGet(FullHttpRequest req, FullHttpResponse resp,String rsp) throws Exception, IOException {
        String protocol = req.getUri();
        String msg = "http.method_get_not_supported";
        resp = new DefaultFullHttpResponse(HTTP_1_1,
                OK, Unpooled.wrappedBuffer(rsp.getBytes("UTF-8")));
        resp.headers().set(CONTENT_TYPE, "text/html");
        resp.headers().set(CONTENT_LENGTH, resp.content().readableBytes());
        if (protocol.endsWith("1.1")) {
            System.out.println(msg);
        } else {
            System.out.println(msg);
        }

    }
    public void doPost(FullHttpRequest req,FullHttpResponse resp) throws Exception, IOException {
        String protocol = req.getUri();
        String msg = "http.method_post_not_supported";
        if (protocol.endsWith("1.1")) {
            System.out.println(msg);
        } else {
            System.out.println(msg);
        }

    }
}
