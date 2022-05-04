package top.lljieeeeee.rpc.transport.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.handler.RequestHandler;
import top.lljieeeeee.rpc.entity.RpcRequest;
import top.lljieeeeee.rpc.serializer.CommonSerializer;
import top.lljieeeeee.rpc.transport.socket.util.ObjectReader;
import top.lljieeeeee.rpc.transport.socket.util.ObjectWriter;

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
    private CommonSerializer serializer;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, CommonSerializer serializer) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serializer = serializer;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream()) {
            RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(inputStream);
            Object response = requestHandler.handle(rpcRequest);
            ObjectWriter.writeObject(outputStream, response, serializer);
        }catch (IOException e){
            logger.info("调用或发送时有错误发生：" + e);
        }
    }
}
