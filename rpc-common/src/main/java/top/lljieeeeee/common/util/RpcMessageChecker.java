package top.lljieeeeee.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.common.entity.RpcRequest;
import top.lljieeeeee.common.entity.RpcResponse;
import top.lljieeeeee.common.enumeration.ResponseCode;
import top.lljieeeeee.common.enumeration.RpcError;
import top.lljieeeeee.common.exception.RpcException;

/**
 * @author Lljieeeeee
 * @date 2022/2/12 10:58
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class RpcMessageChecker {

    private static final Logger logger = LoggerFactory.getLogger(RpcMessageChecker.class);

    private static final String INTERFACE_NAME = "interfaceName";


    private RpcMessageChecker() {

    }

    public static void check(RpcRequest rpcRequest, RpcResponse rpcResponse) {
        if (rpcResponse == null) {
            logger.error("服务调用失败，serviceName：{}", rpcRequest.getInterfaceName());
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + "：" + rpcRequest.getInterfaceName());
        }
        //响应与请求的请求号不同
        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            throw new RpcException(RpcError.RESPONSE_NOT_MATCH, INTERFACE_NAME + "：" + rpcRequest.getInterfaceName());
        }
        //调用失败
        if (rpcResponse.getStatusCode() == null || !rpcResponse.getStatusCode().equals(ResponseCode.SUCCESS.getCode())) {
            logger.error("调用服务失败，serviceName：{}，RpcResponse：{}", rpcRequest.getInterfaceName(), rpcResponse);
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + "：" + rpcRequest.getInterfaceName());
        }
    }
}
