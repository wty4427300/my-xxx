package client;

import idl.Client;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        Client client=new Client();
        try {
            boolean res=client.login("127.0.0.1",8888,"aaa","bbb");
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
