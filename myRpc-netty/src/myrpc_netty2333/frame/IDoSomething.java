package myrpc_netty2333.frame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface IDoSomething {
    String doSomething(Method m, String param)throws InvocationTargetException, IllegalAccessException;
}
