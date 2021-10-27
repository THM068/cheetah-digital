package cheetah.digital.verticles

import cheetah.digital.services.TestService
import io.micronaut.context.BeanContext
import io.vertx.core.AbstractVerticle
import org.redisson.api.RList
import org.redisson.api.RListReactive
import org.redisson.api.RedissonClient
import org.redisson.api.RedissonReactiveClient

class BootStrapVerticle extends AbstractVerticle {
    TestService testService
    RedissonClient redissonClient;

    BootStrapVerticle() {
        BeanContext beanContext = BeanContext.run()
        this.testService = beanContext.getBean(TestService.class);
        this.redissonClient = beanContext.getBean(RedissonClient.class)
    }
    void start() {
        vertx.setPeriodic(1000, id -> {
            // This handler will get called every second
            System.out.println("timer fired!");
            this.testService.printme()
            println this.redissonClient.getId()

            RList<String> rList = redissonClient.getList("kerrieList")
            rList.
            rList.add(25, "Kerrie-channer")
           // rList.add(22, "Thomas Mafela")
            println "startting"
            println rList.size()

            println "Ending"


        });
    }
}
