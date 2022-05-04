package top.lljieeeeee.rpc.serializer;

/**
 * @author Lljieeeeee
 * @date 2022/4/21 13:37
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * 通用的序列化反序列化接口
 */
public interface CommonSerializer {

    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            case 2:
                return new HessianSerializer();
            case 3:
                return new ProtostuffSerializer();
            default:
                return null;
        }
    }
}
