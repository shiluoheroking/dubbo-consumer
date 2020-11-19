package com.dubbo.consumer.dubboconsumer;

import com.springboot.dubbo.study.SayHelloService;

/**
 * @author z小赵
 *
 * <p>当SayhelloService 服务不可用时，返回默认的错误信息给客户端</p>
 */
public class ProviderFailMock implements SayHelloService {
    @Override
    public String sayHello() {
        return "server is not available";
    }
}
