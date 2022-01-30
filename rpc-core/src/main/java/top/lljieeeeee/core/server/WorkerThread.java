package top.lljieeeeee.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.common.entity.RpcRequest;
import top.lljieeeeee.common.entity.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 14:36
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class WorkerThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(WorkerThread.class);

    private Socket socket;

    private Object server;

    public WorkerThread(Socket socket, Object server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())){
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Method method = server.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            Object invoke = method.invoke(server, rpcRequest.getParameters());
            objectOutputStream.writeObject(RpcResponse.success(invoke));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error("调用或发送时有错误发生：", e);
        }
    }
}
