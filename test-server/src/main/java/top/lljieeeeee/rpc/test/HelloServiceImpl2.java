package top.lljieeeeee.rpc.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.api.HelloObject;
import top.lljieeeeee.rpc.api.HelloService;

/**
 * @author Lljieeeeee
 * @date 2022/4/18 22:57
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * 服务端API实现
 */
public class HelloServiceImpl2 implements HelloService {

    /**
     * 使用 HelloServiceImpl 初始化日志对象，方便在日志输出的时候，可以打印出日志信息所属的类
     */
    public static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl2.class);

    @Override
    public String hello(HelloObject object) {
        //使用{}可以直接将getMessage()内容输出
        logger.info("接收到了：{}", object.getMessage());
        return "本次处理来自Socket服务";
    }
}
