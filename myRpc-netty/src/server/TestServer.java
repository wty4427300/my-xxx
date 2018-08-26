package server;



import myrpc_netty2333.frame.IDoSomething;
import myrpc_netty2333.frame.Server;

import java.io.IOException;

/**
 * Created by andilyliao on 17-4-9.
 */
public class TestServer {
    public static void main(String[] args) {
        Server s=new Server(8888);
        Login login=new Login();
        s.start(login);//Login  ILogin  Idosomething
    }
}
