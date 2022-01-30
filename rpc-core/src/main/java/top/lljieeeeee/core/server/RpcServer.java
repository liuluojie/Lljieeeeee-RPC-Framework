package top.lljieeeeee.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 14:23
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class RpcServer {

    private final ExecutorService threadPool;

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    public RpcServer() {
        Integer corePoolSize = 5;
        Integer maximumPoolSize = 50;
        Long keepAliveTime = 60L;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    public void register(Object service, Integer port) {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            logger.info("服务器正在启动。。。。。。");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("客户端连接。。。IP：" + socket.getInetAddress());
                threadPool.execute(new WorkerThread(socket, service));
            }
        }catch (IOException e) {
            logger.info("连接时有错误发生：" + e);
        }

    }
}
