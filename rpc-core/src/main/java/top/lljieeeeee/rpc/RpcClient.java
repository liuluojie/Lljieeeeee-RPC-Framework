package top.lljieeeeee.rpc;

import top.lljieeeeee.rpc.entity.RpcRequest;

/**
 * @author Lljieeeeee
 * @date 2022/4/20 21:49
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * 客户端通用接口
 */
public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);
}