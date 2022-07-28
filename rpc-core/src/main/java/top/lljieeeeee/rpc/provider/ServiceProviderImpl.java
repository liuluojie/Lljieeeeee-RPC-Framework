package top.lljieeeeee.rpc.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.enumeration.RpcError;
import top.lljieeeeee.rpc.exception.RpcException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lljieeeeee
 * @date 2022/4/19 14:27
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * 默认的服务注册表，保存服务端本地服务
 */
public class ServiceProviderImpl implements ServiceProvider {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderImpl.class);

    /**
     * key：服务名称（即接口名）
     * value：服务实体（即实现类的实例对象）
     */
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    /**
     * 用来存放服务名称（即接口名）
     */
    private static final Set registeredService = ConcurrentHashMap.newKeySet();

    /**
     * 保存服务到本地服务注册表
     * @param service 服务实现类对象
     * @param serviceName 服务类
     * @param <T>
     */
    @Override
    public <T> void addServiceProvider(T service, String serviceName) {
        if (registeredService.contains(serviceName)) {
            return;
        }
        registeredService.add(serviceName);
        serviceMap.put(serviceName, service);
        logger.info("向接口：{} 注册服务：{}", service.getClass().getInterfaces(), serviceName);
    }

    @Override
    public Object getServiceProvider(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUNT);
        }
        return service;
    }
}
