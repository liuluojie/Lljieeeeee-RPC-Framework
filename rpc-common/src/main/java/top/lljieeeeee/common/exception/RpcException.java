package top.lljieeeeee.common.exception;

import top.lljieeeeee.common.enumeration.RpcError;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 17:29
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }

}