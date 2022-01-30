package top.lljieeeeee.common.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 13:44
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
@Data
@Builder
public class RpcRequest implements Serializable {

    /**
     * 被调用接口的名称
     */
    private String interfaceName;

    /**
     * 被调用方法的名称
     */
    private String methodName;

    /**
     * 被调用方法的参数
     */
    private Object[] parameters;

    /**
     * 被调用方法的参数类型
     */
    private Class<?>[] paramTypes;
}
