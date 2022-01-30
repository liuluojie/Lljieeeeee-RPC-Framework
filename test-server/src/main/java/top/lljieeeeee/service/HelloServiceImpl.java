package top.lljieeeeee.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeeee.api.HelloObject;
import top.lljieeeeeee.api.HelloService;


/**
 * @author Lljieeeeee
 * @date 2022/1/30 13:34
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject helloObject) {
        logger.info("接收到了：{}", helloObject.getMessage());
        return "这个是调用到返回值。id = " + helloObject.getId();
    }
}
