package top.lljieeeeeee.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 13:31
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
@Data
@AllArgsConstructor
public class HelloObject implements Serializable {

    private Integer id;

    private String message;
}
