package top.lljieeeeee.core.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.common.entity.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 14:13
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class RpbClient {

    private static final Logger logger = LoggerFactory.getLogger(RpbClient.class);

    public Object sendRequest(RpcRequest rpcRequest, String host, Integer port) {
        try (Socket socket = new Socket(host, port)){
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e) {
            logger.error("调用时有错误发生：" + e);
            return null;
        }
    }
}
