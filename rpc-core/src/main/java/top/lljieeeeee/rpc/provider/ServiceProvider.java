package top.lljieeeeee.rpc.provider;

/**
 * @author Lljieeeeee
 * @date 2022/5/4 15:16
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * 保存和提供服务实例对象
 */
public interface ServiceProvider {

    <T> void addServiceProvider(T service, String serviceName);

    Object getServiceProvider(String serviceName);
}
