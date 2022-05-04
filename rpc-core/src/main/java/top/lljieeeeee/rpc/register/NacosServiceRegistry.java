package top.lljieeeeee.rpc.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.enumeration.RpcError;
import top.lljieeeeee.rpc.exception.RpcException;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Lljieeeeee
 * @date 2022/5/4 15:29
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * Nacos服务注册中心
 */
public class NacosServiceRegistry implements ServiceRegistry{

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    private static final String SERVER_ADDRESS = "127.0.0.1:8848";
    private static final NamingService namingService;

    static {
        try {
            //连接Nacos创建命名服务
            namingService = NamingFactory.createNamingService(SERVER_ADDRESS);
        }catch (NacosException e) {
            logger.error("连接Nacos时有错误发生：" + e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    /**
     * 将服务的名称和地址注册进服务注册中心
     * @param serviceName
     * @param inetSocketAddress
     */
    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            //向Nacos注册服务
            namingService.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
        }catch (NacosException e) {
            logger.error("注册服务时有错误发生" + e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

    /**
     * 根据服务名称从注册中心获取到一个服务提供者的地址
     * @param serviceName
     * @return
     */
    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = namingService.getAllInstances(serviceName);
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        }catch (NacosException e) {
            logger.error("获取服务是有错误发生");
        }
        return null;
    }
}
