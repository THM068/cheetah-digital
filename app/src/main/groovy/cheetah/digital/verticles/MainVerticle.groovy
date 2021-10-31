package cheetah.digital.verticles

import cheetah.digital.dao.CustomerBloomFilterRepository
import cheetah.digital.dao.CustomerRedissonRepository
import cheetah.digital.model.Customer
import groovy.transform.CompileStatic
import io.micronaut.context.BeanContext
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import org.redisson.api.RBucket

@CompileStatic
class MainVerticle extends AbstractVerticle {
    CustomerRedissonRepository customerRedissonRepository

    MainVerticle() {
        BeanContext beanContext = BeanContext.run()
        this.customerRedissonRepository = beanContext.getBean(CustomerRedissonRepository.class);
    }
    void start(Promise<Void> startPromise) {
        vertx.setPeriodic(1000, id -> {
           println("Tickiing ...")
        });
        startPromise.complete()
    }

    void stop() {
        println("Closing the jedispool")
    }
}
