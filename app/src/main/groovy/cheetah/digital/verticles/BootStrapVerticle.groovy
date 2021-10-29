package cheetah.digital.verticles

import cheetah.digital.repository.CustomerRepository
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
    CustomerRepository customerRepository

    BootStrapVerticle() {
        BeanContext beanContext = BeanContext.run()
        this.customerRepository = beanContext.getBean(CustomerRepository.class);
    }
    void start() {
        vertx.setPeriodic(1000, id -> {
            // This handler will get called every second
            System.out.println("timer fired!");

            RBucket<String> bucket = this.customerRepository.redissonClient.getBucket("stringObject")
            bucket.set("Rommel is the object value")
            String objValue = bucket.get();
            println objValue
        });
    }

    void stop() {
        println("Closing the jedispool")
    }
}
