package top.lljieeeeee.rpc.test;

import top.lljieeeeee.rpc.annotation.Service;
import top.lljieeeeee.rpc.api.ByeService;

/**
 * @author liuluojie
 * @date 2022/6/23 14:35
 */
@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String name) {
        return "bye: " + name;
    }
}
