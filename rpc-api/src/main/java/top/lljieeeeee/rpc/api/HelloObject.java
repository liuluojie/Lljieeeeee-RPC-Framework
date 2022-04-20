package top.lljieeeeee.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Lljieeeeee
 * @date 2022/4/18 22:28
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 *
 *
 */
//自动加上所有属性的 get set toString hashCode equals方法
@Data
@NoArgsConstructor
//添加一个含有所有已声明字段属性参数的构造函数
@AllArgsConstructor

public class HelloObject implements Serializable {
    /**
     * Serializable 序列化接口没有任何方法或字段，只是用于标识可序列化的语义。
     * 实现了 Serializable 接口的类可以被 ObjectOutputStream 转换为字节流。
     * 同时也可以通过 ObjectInputStream 将其解析为对象。
     * 序列化是指把对象转换为字节序列的过程；反序列化则是把持久化的字节文件数据恢复为对象的过程。
     */

    private Integer id;

    private String message;
}
