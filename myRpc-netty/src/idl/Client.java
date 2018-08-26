package idl;

import myrpc_netty2333.frame.Send;

import java.io.IOException;



public class Client {
    public boolean login(String ip,int port,String username,String password) throws IOException {
        Send send=new Send(port,ip);
        String res=send.something("idl.ILogin|login|"+username+"|"+password);
        System.out.println(res);
        send.start();
        switch (res){
            case "TRUE＋ok":
                return true;
            default:
                return false;
        }
    }
}
