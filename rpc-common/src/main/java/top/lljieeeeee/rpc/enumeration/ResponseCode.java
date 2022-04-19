package top.lljieeeeee.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Lljieeeeee
 * @date 2022/4/18 22:50
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS(200, "调用方法成功"),
    FAIL(500, "调用方法失败"),
    METHOD_NOT_FOUND(500, "未找到指定方法"),
    CLASS_NOT_FOUND(500, "未找到指定类");

    /**
     * 响应状态码
     */
    private final Integer code;

    /**
     * 响应信息
     */
    private final String message;
}
