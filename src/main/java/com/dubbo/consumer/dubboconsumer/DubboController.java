package com.dubbo.consumer.dubboconsumer;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dubbo.study.SayHelloService;

@RestController
public class DubboController {

    /**
     * loadbalance属性：指定负载均衡策略，dubbo提供的策略有：
     *  1. random:           随机 + 权重 的方式对服务集群进行访问
     *  2. roundrobin:       轮询 + 权重
     *  3. leastactive:      最小活跃，服务器单位时间处理能力越高，则该机器的活跃度值越小，那么该机器可以接受更多的请求
     *  4. consistenthash:   一致性hash算法
     *
     * cluster属性：用于配置容错能力，dubbo提供的策略有：
     *  1. failover:  失败重试，通过 retries 参数指定重试次数，服务调用总数为：retires + 1（默认容错策略）
     *  2. failfast:  快速失败，一般适用于非幂等性的上行操作
     *  3. failsafe:  失败安全，出现异常直接忽略，通常用于写入日志等操作
     *  4. failback:  失败自动恢复，后台记录失败请求，定时重发，通常用语消息通知
     *  5. forking:   并行调用多个服务器，只要有一个成功就返回，对于性能要求高的服务可以采用该策略，有点类似于常说的双发机制
     *  6. broadcast: 广播调用所有provider，任意一个provider报错则报错，通常用于更新缓存的操作
     *
     *  check属性：启动时检查依赖的服务是否正常启动，生产环境中，可能会出现A服务和B服务相互依赖，此时应该讲check设置为false来避免服务检查
     *
     *  mock属性：服务降级。当SayHelloService服务异常时，可以指定特定异常信息返回给客户端
     *
     */
    @Reference(loadbalance = "roundrobin", cluster = "failover", retries = 2, check = false,
            mock = "com.dubbo.consumer.dubboconsumer.ProviderFailMock")
    private SayHelloService sayHelloService;

    @GetMapping("/sayHello")
    public String sayHello() {
        return sayHelloService.sayHello();
    }
}
