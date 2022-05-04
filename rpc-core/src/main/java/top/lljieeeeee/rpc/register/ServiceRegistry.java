package top.lljieeeeee.rpc.register;

import java.net.InetSocketAddress;

/**
 * @author Lljieeeeee
 * @date 2022/5/4 15:27
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * 服务注册接口
 */
public interface ServiceRegistry {

    /**
     * 将一个服务注册到注册表
     * @param serviceName
     * @param inetSocketAddress
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

}
