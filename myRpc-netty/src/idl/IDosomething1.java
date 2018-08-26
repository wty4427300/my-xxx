package idl;

import myrpc_netty2333.frame.IDoSomething;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class IDosomething1 implements IDoSomething {
    public String doSomething(Method m, String param) throws InvocationTargetException, IllegalAccessException {
        boolean b=false;
        String str[]=param.split("\\|");
        System.out.println(str[2]+str[3]);
        if(str[2].equals("aaa")&&str[3].equals("ccc")) {
             b= (boolean) m.invoke(this,str[2],str[3]);
        }
        System.out.println(b+" "+"new");
        if (b){
            return "TRUE登录成功";
        }else{
            return "FALSE账号或者密码错误";
        }
    }
}