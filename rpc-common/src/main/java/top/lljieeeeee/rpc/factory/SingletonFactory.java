package top.lljieeeeee.rpc.factory;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Lljieeeeee
 * @date 2022/5/4 17:28
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class SingletonFactory {

    private static Map<Class, Object> objectMap = new TreeMap<>();

    public SingletonFactory() {

    }

    public static <T> T getInstance(Class<T> clazz) {
        Object instance = objectMap.get(clazz);
        if (instance == null) {
            synchronized (clazz) {
                if (instance == null) {
                    try {
                        instance = clazz.newInstance();
                        objectMap.put(clazz, instance);
                    }catch (IllegalAccessException | InstantiationException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        }
        return clazz.cast(instance);
    }
}
