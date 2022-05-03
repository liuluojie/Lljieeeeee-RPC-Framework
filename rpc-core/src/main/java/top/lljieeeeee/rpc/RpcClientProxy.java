package top.lljieeeeee.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.socket.client.SocketClient;
import top.lljieeeeee.rpc.entity.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author Lljieeeeee
 * @date 2022/4/19 13:07
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class RpcClientProxy implements InvocationHandler {

    public static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);

    private final RpcClient client;

    public RpcClientProxy(RpcClient client) {
        this.client = client;
    }

    //抑制编译器产生警告信息
    @SuppressWarnings("unckecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args){
        logger.info("调用方法：{}#{}", method.getDeclaringClass().getName(), method.getName());
        RpcRequest rpcRequest = new RpcRequest(UUID.randomUUID().toString(), method.getDeclaringClass().getName(),
                method.getName(), args, method.getParameterTypes());
        return client.sendRequest(rpcRequest);
    }
}
