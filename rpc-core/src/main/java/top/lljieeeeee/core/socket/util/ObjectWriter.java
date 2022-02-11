package top.lljieeeeee.core.socket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.common.entity.RpcRequest;
import top.lljieeeeee.common.enumeration.PackageType;
import top.lljieeeeee.core.serializer.CommonSerializer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Lljieeeeee
 * @date 2022/2/11 21:14
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class ObjectWriter {

    private static final Logger logger = LoggerFactory.getLogger(ObjectWriter.class);

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    public static void writeObject(OutputStream out, Object object, CommonSerializer serializer) throws IOException {
        out.write(intToBytes(MAGIC_NUMBER));
        if (object instanceof RpcRequest) {
            out.write(intToBytes(PackageType.REQUEST_PACK.getCode()));
        }else {
            out.write(intToBytes(PackageType.RESPONSE_PACK.getCode()));
        }
        out.write(intToBytes(serializer.getCode()));
        byte[] bytes = serializer.serialize(object);
        out.write(intToBytes(bytes.length));
        out.write(bytes);
        out.flush();
    }

    private static byte[] intToBytes(int value) {
        byte[] bytes = new byte[4];
        bytes[3] =  (byte) ((value>>24) & 0xFF);
        bytes[2] =  (byte) ((value>>16) & 0xFF);
        bytes[1] =  (byte) ((value>>8) & 0xFF);
        bytes[0] =  (byte) (value & 0xFF);
        return bytes;
    }
}
