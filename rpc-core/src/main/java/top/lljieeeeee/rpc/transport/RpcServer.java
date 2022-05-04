package top.lljieeeeee.rpc.transport;

import top.lljieeeeee.rpc.serializer.CommonSerializer;

/**
 * @author Lljieeeeee
 * @date 2022/4/20 21:49
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * 服务端通用接口
 */
public interface RpcServer {

    void start();

    void setSerializer(CommonSerializer serializer);

    /**
     * 向Nacos注册服务
     * @param service
     * @param serviceClass
     * @param <T>
     */
    <T> void publishService(Object service, Class<T> serviceClass);
}
