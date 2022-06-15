package top.lljieeeeee.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;
import top.lljieeeeee.rpc.factory.SingletonFactory;

import java.util.List;
import java.util.Random;

/**
 * @author liuluojie
 * @date 2022/6/7 13:47
 */
public class RandomLoadBalancer implements LoadBalancer{

    @Override
    public Instance select(List<Instance> instances) {
        return instances.get(SingletonFactory.getInstance(Random.class).nextInt(instances.size()));
    }
}
