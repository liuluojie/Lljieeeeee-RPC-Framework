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
public class RequestHandler implements Runnable{

    public static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket socket;
    private Object service;

    public RequestHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object resultObject = invokeMethod(rpcRequest);
            objectOutputStream.writeObject(RpcResponse.success(resultObject));
            objectOutputStream.flush();
        }catch (IOException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e){
            logger.info("调用或发送时有错误发生：" + e);
        }
    }

    private Object invokeMethod(RpcRequest rpcRequest) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = Class.forName(rpcRequest.getInterfaceName());
        // 判断是否为同一类型或存在父子、接口关系
        if (!clazz.isAssignableFrom(service.getClass())) {
            return RpcResponse.fail(ResponseCode.CLASS_NOT_FOUND);
        }
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
        }
        return method.invoke(service, rpcRequest.getParameters());
    }
}
