package top.lljieeeeee.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author Lljieeeeee
 * @date 2022/4/19 13:32
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class RpcServer {

    public static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private final ExecutorService threadPool;

    public RpcServer() {
        int corePoolSize = 5;
        int maximumPoolSize = 50;
        long keepAliveTime = 60;

        /**
         * 设置上限为100个线程的阻塞队列
         */
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        /**
         * 创建线程池实例
         */
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    public void register(Object service, int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器正在启动。。。");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("客户端连接！IP：" + socket.getInetAddress() + ":" + socket.getPort());
                threadPool.execute(new RequestHandler(socket, service));
            }
        } catch (IOException e) {
            logger.error("连接时有错误发生：" + e);
        }
    }
}
