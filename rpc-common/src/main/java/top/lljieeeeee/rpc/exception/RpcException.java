package top.lljieeeeee.rpc.exception;

import top.lljieeeeee.rpc.enumeration.RpcError;

/**
 * @author Lljieeeeee
 * @date 2022/4/19 14:00
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class RpcException extends RuntimeException{

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }
}
