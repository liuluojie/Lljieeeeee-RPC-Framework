package top.lljieeeeee.rpc.test.socketserver.implmethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.api.HelloObject;
import top.lljieeeeee.rpc.api.HelloService;

/**
 * @author liuluojie
 * @date 2022/6/7 14:20
 */
public class HelloServiceImpl2 implements HelloService {

    public static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl2.class);

    @Override
    public String hello(HelloObject object) {
        //使用{}可以直接将getMessage()内容输出
        logger.info("接收到了：{}", object.getMessage());
        return "本次处理来自Socket服务，服务器为：server-2";
    }
}
