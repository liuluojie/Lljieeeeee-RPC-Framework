package top.lljieeeeee.rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.entity.RpcRequest;
import top.lljieeeeee.rpc.entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Lljieeeeee
 * @date 2022/4/19 13:07
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class RpcClientProxy implements InvocationHandler {

    public static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);

    private String host;
    private Integer port;

    public RpcClientProxy(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    //抑制编译器产生警告信息
    @SuppressWarnings("unckecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("调用方法：{}#{}", method.getDeclaringClass().getName(), method.getName());
        //客户端向服务端传输的对象，Builder模式生成，利用反射获取相关信息
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        RpcClient rpcClient = new RpcClient();
        return rpcClient.sendRequest(rpcRequest, host, port);
    }
}
