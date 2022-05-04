package top.lljieeeeee.rpc.transport;

import top.lljieeeeee.rpc.entity.RpcRequest;
import top.lljieeeeee.rpc.serializer.CommonSerializer;

/**
 * @author Lljieeeeee
 * @date 2022/4/20 21:49
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * 客户端通用接口
 */
public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer serializer);
}
