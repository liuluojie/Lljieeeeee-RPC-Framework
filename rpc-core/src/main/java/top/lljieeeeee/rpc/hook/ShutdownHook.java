package top.lljieeeeee.rpc.hook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.factory.ThreadPoolFactory;
import top.lljieeeeee.rpc.util.NacosUtil;

import java.util.concurrent.ExecutorService;

/**
 * @author Lljieeeeee
 * @date 2022/5/4 17:41
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class ShutdownHook {

    private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);

    /**
     * 单例模式创建钩子，保证全局只有一个钩子
     */
    private static final ShutdownHook shutdownHook = new ShutdownHook();

    public static ShutdownHook getShutdownHook() {
        return shutdownHook;
    }

    public void addClearAllHook() {
        logger.info("服务端关闭前将注销所有的服务");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtil.clearRegistry();
            //关闭所有线程池
            ThreadPoolFactory.shutDownAll();
        }));
    }
}
