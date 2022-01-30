package top.lljieeeeee.core.client;

import top.lljieeeeee.common.entity.RpcRequest;
import top.lljieeeeee.common.entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 14:01
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class RpcClientProxy implements InvocationHandler {

    private String host;
    private Integer port;

    public RpcClientProxy(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    /**
     *
     * @param clazz
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getSimpleName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        RpbClient rpbClient = new RpbClient();
        return ((RpcResponse) rpbClient.sendRequest(rpcRequest, host, port)).getData();
    }
}
