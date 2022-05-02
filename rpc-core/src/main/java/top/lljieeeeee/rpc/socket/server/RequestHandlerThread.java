package top.lljieeeeee.rpc.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.RequestHandler;
import top.lljieeeeee.rpc.entity.RpcRequest;
import top.lljieeeeee.rpc.entity.RpcResponse;
import top.lljieeeeee.rpc.registry.ServiceRegistry;
import top.lljieeeeee.rpc.serializer.CommonSerializer;
import top.lljieeeeee.rpc.socket.util.ObjectReader;
import top.lljieeeeee.rpc.socket.util.ObjectWriter;

import java.io.*;
import java.net.Socket;

/**
 * @author Lljieeeeee
 * @date 2022/4/19 14:37
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * IO传输模式|处理客户端RpcRequest的工作线程
 */
public class RequestHandlerThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;
    private CommonSerializer serializer;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry, CommonSerializer serializer) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
        this.serializer = serializer;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream()) {
            RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(inputStream);
            String interfaceName = rpcRequest.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handle(rpcRequest, service);
            RpcResponse<Object> response = RpcResponse.success(result);
            ObjectWriter.writeObject(outputStream, response, serializer);
        }catch (IOException e){
            logger.info("调用或发送时有错误发生：" + e);
        }
    }
}
