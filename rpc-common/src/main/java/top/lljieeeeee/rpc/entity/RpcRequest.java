package top.lljieeeeee.rpc.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lljieeeeee
 * @date 2022/4/18 22:39
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
@Data
//使用创建者模式，一次性给所有变量初始赋值
@Builder
public class RpcRequest implements Serializable {

    /**
     * 待调用接口名称
     */
    private String interfaceName;

    /**
     * 待调用方法名称
     */
    private String methodName;

    /**
     * 待调用方法的参数
     */
    private Object[] parameters;

    /**
     * 待调用方法的参数类型
     */
    private Class<?>[] paramTypes;
}
