package top.lljieeeeee.core.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.common.entity.RpcRequest;
import top.lljieeeeee.common.entity.RpcResponse;
import top.lljieeeeee.common.enumeration.ResponseCode;
import top.lljieeeeee.common.enumeration.RpcError;
import top.lljieeeeee.common.exception.RpcException;
import top.lljieeeeee.core.RpcClient;
import top.lljieeeeee.core.serializer.CommonSerializer;
import top.lljieeeeee.core.socket.util.ObjectReader;
import top.lljieeeeee.core.socket.util.ObjectWriter;

import java.io.*;
import java.net.Socket;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 14:13
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class SocketClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private final String host;
    private final int port;

    private CommonSerializer serializer;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        try (Socket socket = new Socket(host, port)) {
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
            Object obj = ObjectReader.readObject(inputStream);
            RpcResponse rpcResponse = (RpcResponse) obj;
            if(rpcResponse == null) {
                logger.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            if(rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            return rpcResponse.getData();
        } catch (IOException e) {
            logger.error("调用时有错误发生：", e);
            throw new RpcException("服务调用失败: ", e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
