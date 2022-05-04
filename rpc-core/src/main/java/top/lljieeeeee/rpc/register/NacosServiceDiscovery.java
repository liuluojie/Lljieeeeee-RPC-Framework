package top.lljieeeeee.rpc.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.util.NacosUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Lljieeeeee
 * @date 2022/5/4 17:06
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class NacosServiceDiscovery implements ServiceDiscovery{

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceDiscovery.class);

    /**
     * 根据服务名称从注册中心获取到一个服务提供者的地址
     * @param serviceName
     * @return
     */
    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            //利用列表获取某个服务的所有提供者
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        }catch (NacosException e) {
            logger.error("获取服务时有错误发生：", e);
        }
        return null;
    }
}
