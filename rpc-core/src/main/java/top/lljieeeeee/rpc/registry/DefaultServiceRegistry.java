package top.lljieeeeee.rpc.registry;

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
 * 默认服务注册表
 */
public class DefaultServiceRegistry implements ServiceRegistry{

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceRegistry.class);

    /**
     * key：服务名称（即接口名）
     * value：服务实体（即实现类的实例对象）
     */
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    /**
     * 用来存放实现类的名称，Set存取更高效，存放实现类名称相比存放接口名称占的空间更小，因为一个实现类可能实现了多个接口，查找效率也会更高
     */
    private final Set registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized  <T> void register(T service) {
        String serviceImplName = service.getClass().getCanonicalName();
        if (registeredService.contains(serviceImplName)) {
            return;
        }
        registeredService.add(serviceImplName);
        //可能实现了多个接口， 所有使用Class数组接收
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }
        logger.info("向接口：{} 注册服务：{}", interfaces, serviceImplName);
    }

    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUNT);
        }
        return service;
    }
}
