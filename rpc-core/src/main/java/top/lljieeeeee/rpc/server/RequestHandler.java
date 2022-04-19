package top.lljieeeeee.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.entity.RpcRequest;
import top.lljieeeeee.rpc.entity.RpcResponse;
import top.lljieeeeee.rpc.enumeration.ResponseCode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    private Socket socket;
    private Object service;

    public Object handle(RpcRequest rpcRequest, Object service) {
        Object result = null;
        try {
            result = invokeMethod(rpcRequest, service);
            logger.info("服务：{} 成功调用方法：{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (InvocationTargetException | IllegalAccessException e) {
            logger.error("调用或发送时有错误发生：" + e);
        }
        return result;
    }

    private Object invokeMethod(RpcRequest rpcRequest, Object service) throws InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            //getClass()获取的是实例对象的类型
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
        }
        return method.invoke(service, rpcRequest.getParameters());
    }
}
