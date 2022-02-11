package top.lljieeeeee.core.socket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.common.entity.RpcRequest;
import top.lljieeeeee.common.entity.RpcResponse;
import top.lljieeeeee.common.enumeration.PackageType;
import top.lljieeeeee.common.enumeration.RpcError;
import top.lljieeeeee.common.exception.RpcException;
import top.lljieeeeee.core.serializer.CommonSerializer;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Lljieeeeee
 * @date 2022/2/11 21:03
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class ObjectReader {

    private static final Logger logger = LoggerFactory.getLogger(ObjectReader.class);

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    public static Object readObject(InputStream in) throws IOException {
        byte[] numBytes = new byte[4];
        in.read(numBytes);
        int magic = bytesToInt(numBytes);
        if (magic != MAGIC_NUMBER) {
            logger.error("不识别的协议包：{}", magic);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }
        in.read(numBytes);
        int packageCode = bytesToInt(numBytes);
        Class<?> packageClass;
        if (packageCode == PackageType.REQUEST_PACK.getCode()) {
            packageClass = RpcRequest.class;
        }else if (packageCode == PackageType.RESPONSE_PACK.getCode()) {
            packageClass = RpcResponse.class;
        }else {
            logger.error("不识别的数据包：{}", packageCode);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }
        in.read(numBytes);
        int serializerCode = bytesToInt(numBytes);
        CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);
        if (serializer == null) {
            logger.error("不识别的反序列化器：{}", serializerCode);
            throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
        }
        in.read(numBytes);
        int length = bytesToInt(numBytes);
        byte[] bytes = new byte[length];
        in.read(bytes);
        return serializer.deserialize(bytes, packageClass);
    }

    private static int bytesToInt(byte[] src) {
        int value;
        value = (src[0] & 0xFF)
                | ((src[1] & 0xFF)<<8)
                | ((src[2] & 0xFF)<<16)
                | ((src[3] & 0xFF)<<24);
        return value;
    }
}
