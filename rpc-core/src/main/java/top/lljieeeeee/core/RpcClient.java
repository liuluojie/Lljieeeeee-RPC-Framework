package top.lljieeeeee.core;

import top.lljieeeeee.common.entity.RpcRequest;
import top.lljieeeeee.core.serializer.CommonSerializer;

/**
 * @author Lljieeeeee
 * @date 2022/2/2 13:08
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public interface RpcClient {

    /**
     * 发送请求
     * @param rpcRequest
     * @return
     */
    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer serializer);
}
