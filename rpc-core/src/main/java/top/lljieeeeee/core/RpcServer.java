package top.lljieeeeee.core;

import top.lljieeeeee.core.serializer.CommonSerializer;

/**
 * @author Lljieeeeee
 * @date 2022/2/2 13:09
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public interface RpcServer {

    /**
     * 启动服务
     * @param port
     */
    void start(int port);

    void setSerializer(CommonSerializer serializer);
}
