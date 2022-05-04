package top.lljieeeeee.rpc.register;

import java.net.InetSocketAddress;

/**
 * @author Lljieeeeee
 * @date 2022/5/4 17:06
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * 服务发现接口
 */
public interface ServiceDiscovery {

    /**
     * 根据服务名查找服务端地址
     * @param serviceName
     * @return
     */
    InetSocketAddress lookupService(String serviceName);
}
