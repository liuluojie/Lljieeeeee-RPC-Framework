package top.lljieeeeee.common.entity;

import lombok.Data;
import top.lljieeeeee.common.enumeration.ResponseCode;

import java.io.Serializable;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 13:47
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
@Data
public class RpcResponse<T> implements Serializable {

    public RpcResponse() {}

    /**
     * 响应状态码
     */
    private Integer statusCode;

    /**
     * 响应状态码对应的信息
     */
    private String message;

    /**
     * 成功时的响应数据
     */
    private T data;

    /**
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}
