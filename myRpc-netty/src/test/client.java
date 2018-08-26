package test;

import myrpc_netty2333.frame.Send;

public class client {
    public static void main(String[] args) {
        Send send=new Send(8888,"127.0.0.1");
    }
}
