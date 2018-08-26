package server;

import idl.ILogin;


/**
 * Created by andilyliao on 17-4-9.
 */
public class Login extends ILogin {
    @Override
    public boolean login(String username, String password) {
        System.out.println("服务器：login");
        return true;
    }
}
