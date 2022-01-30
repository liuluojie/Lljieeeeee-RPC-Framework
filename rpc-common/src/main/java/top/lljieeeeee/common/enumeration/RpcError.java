package top.lljieeeeee.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 17:28
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
@AllArgsConstructor
@Getter
public enum RpcError {

    SERVICE_INVOCATION_FAILURE("服务调用出现失败"),
    SERVICE_NOT_FOUND("找不到对应的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口");

    private final String message;

}