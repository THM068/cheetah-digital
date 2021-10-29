package cheetah.digital.verticles

import cheetah.digital.services.TestService
import groovy.transform.CompileStatic
import io.micronaut.context.BeanContext
import io.vertx.core.AbstractVerticle
import org.redisson.api.RBucket
import org.redisson.api.RedissonClient
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
@CompileStatic
class BootStrapVerticle extends AbstractVerticle {
    TestService testService
    JedisPool jedisPool
    RedissonClient redissonClient;

    BootStrapVerticle() {
        BeanContext beanContext = BeanContext.run()
        this.testService = beanContext.getBean(TestService.class);
        this.jedisPool = beanContext.getBean(JedisPool.class)
        this.redissonClient = beanContext.getBean(RedissonClient.class)
    }
    void start() {
        vertx.setPeriodic(1000, id -> {
            // This handler will get called every second
            System.out.println("timer fired!");
            this.testService.printme()

            RBucket<String> bucket = redissonClient.getBucket("stringObject")
            bucket.set("Rommel is the object value")
            String objValue = bucket.get();
            println objValue



        });
    }

    void stop() {
        println("Closing the jedispool")
        this.jedisPool.close()
    }
}
