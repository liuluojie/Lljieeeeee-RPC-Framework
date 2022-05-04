package top.lljieeeeee.rpc.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.entity.RpcRequest;
import top.lljieeeeee.rpc.entity.RpcResponse;
import top.lljieeeeee.rpc.enumeration.ResponseCode;
import top.lljieeeeee.rpc.provider.ServiceProvider;
import top.lljieeeeee.rpc.provider.ServiceProviderImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author Lljieeeeee
 * @date 2022/4/19 13:25
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * 实际执行方法调用任务的工作线程
 */
public class RequestHandler{

    public static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final ServiceProvider serviceProvider;

    static {
        serviceProvider = new ServiceProviderImpl();
    }

    public Object handle(RpcRequest rpcRequest) {
        Object result = null;
        //从服务端本地注册表中获取服务实体
        Object service = RequestHandler.serviceProvider.getServiceProvider(rpcRequest.getInterfaceName());
        try {
            result = invokeMethod(rpcRequest, service);
            logger.info("服务：{} 成功调用方法：{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (InvocationTargetException | IllegalAccessException e) {
            logger.error("调用或发送时有错误发生：" + e);
        }
        //方法调用成功
        return RpcResponse.success(result, rpcRequest.getRequestId());
    }

    private Object invokeMethod(RpcRequest rpcRequest, Object service) throws InvocationTargetException, IllegalAccessException {
        Method method = null;
        try {
            //getClass()获取的是实例对象的类型
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            //方法调用失败
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND, rpcRequest.getRequestId());
        }
        return method.invoke(service, rpcRequest.getParameters());
    }
}
