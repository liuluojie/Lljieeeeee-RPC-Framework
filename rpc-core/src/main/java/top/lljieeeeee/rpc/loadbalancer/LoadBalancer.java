package top.lljieeeeee.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @author liuluojie
 * @date 2022/6/7 13:44
 * @description 负载均衡接口
 */
public interface LoadBalancer {

    /**
     * 从一系列Instance中选择一个
     * @param instances
     * @return
     */
    Instance select(List<Instance> instances);
}
