package top.lljieeeeee.rpc.transport.netty.client;

import top.lljieeeeee.rpc.entity.RpcResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuluojie
 * @date 2022/6/7 11:26
 * @description 未处理的请求（对所有客户端请求进行统一管理）
 */
public class UnprocessedRequests {

    private static ConcurrentHashMap<String, CompletableFuture<RpcResponse>> unprocessedRequests = new ConcurrentHashMap<>();

    public void put(String requestId, CompletableFuture<RpcResponse> completableFuture) {
        unprocessedRequests.put(requestId, completableFuture);
    }

    public void remove(String requestId) {
        unprocessedRequests.remove(requestId);
    }

    public void complete(RpcResponse rpcResponse) {
        //请求完成，将请求从未完成列表中删除
        CompletableFuture<RpcResponse> completableFuture = unprocessedRequests.remove(rpcResponse.getRequestId());
        if (completableFuture != null) {
            //把响应对象放入Future
            completableFuture.complete(rpcResponse);
        }else {
            throw new IllegalStateException();
        }
    }
}
