package top.lljieeeeee.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 13:49
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS(200,"调用方法成功"),
    FAIL(500,"调用方法失败"),
    METHOD_NOT_FOUND(500,"未找到指定方法"),
    CLASS_NOT_FOUND(500,"未找到指定类");

    private final int code;
    private final String message;

}
