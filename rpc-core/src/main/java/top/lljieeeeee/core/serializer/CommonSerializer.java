package top.lljieeeeee.core.serializer;

/**
 * @author Lljieeeeee
 * @date 2022/2/8 13:45
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public interface CommonSerializer {

    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
