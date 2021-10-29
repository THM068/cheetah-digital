package cheetah.digital.verticles

import cheetah.digital.services.TestService
import groovy.transform.CompileStatic
import io.micronaut.context.BeanContext
import io.vertx.core.AbstractVerticle
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
@CompileStatic
class BootStrapVerticle extends AbstractVerticle {
    TestService testService
    JedisPool jedisPool

    BootStrapVerticle() {
        BeanContext beanContext = BeanContext.run()
        this.testService = beanContext.getBean(TestService.class);
        this.jedisPool = beanContext.getBean(JedisPool.class)
    }
    void start() {
        vertx.setPeriodic(1000, id -> {
            // This handler will get called every second
            System.out.println("timer fired!");
            this.testService.printme()

            try ( Jedis jedis = jedisPool.getResource()) {
                jedis.set("kerrie", "Mafela thomas Alexandra")
                println "startting"
                println jedis.get("kerrie")
                println "Ending"
            }

        });
    }

    void stop() {
        println("Closing the jedispool")
        this.jedisPool.close()
    }
}
