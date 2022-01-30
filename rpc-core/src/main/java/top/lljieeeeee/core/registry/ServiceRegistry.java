package top.lljieeeeee.core.registry;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 17:33
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public interface ServiceRegistry {

    /**
     * 将一个服务注册进注册表
     * @param service 待注册的服务实体
     * @param <T> 服务实体类
     */
    <T> void register(T service);

    /**
     * 根据服务名称获取服务实体
     * @param serviceName 服务名称
     * @return 服务实体
     */
    Object getService(String serviceName);

}

