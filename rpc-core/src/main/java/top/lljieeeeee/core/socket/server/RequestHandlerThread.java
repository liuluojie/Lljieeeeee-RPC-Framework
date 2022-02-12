package top.lljieeeeee.core.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.common.entity.RpcRequest;
import top.lljieeeeee.common.entity.RpcResponse;
import top.lljieeeeee.core.RequestHandler;
import top.lljieeeeee.core.registry.ServiceRegistry;
import top.lljieeeeee.core.serializer.CommonSerializer;
import top.lljieeeeee.core.socket.util.ObjectReader;
import top.lljieeeeee.core.socket.util.ObjectWriter;

import java.io.*;
import java.net.Socket;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 14:36
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class RequestHandlerThread implements Runnable {

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
            RpcResponse<Object> response = RpcResponse.success(result, rpcRequest.getRequestId());
            ObjectWriter.writeObject(outputStream, response, serializer);
        } catch (IOException e) {
            logger.error("调用或发送时有错误发生：", e);
        }
    }

}