package cheetah.digital.verticles

import cheetah.digital.constants.DB
import cheetah.digital.dao.CustomerBloomFilterRepository
import cheetah.digital.dao.CustomerRedissonRepository
import cheetah.digital.model.Customer
import groovy.transform.CompileStatic
import io.micronaut.context.BeanContext
import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import org.redisson.api.RBucket
import org.redisson.api.RedissonClient

import java.util.logging.Logger

import static cheetah.digital.constants.DB.CUSTOMER_PROCESS_EVENT_BUS
import static cheetah.digital.constants.DB.STREAMING_QUEUE

@CompileStatic
class MainVerticle extends AbstractVerticle {
    private RedissonClient redissonClient
    static Logger logger = Logger.getLogger("MainVerticle")

    MainVerticle() {
        BeanContext beanContext = BeanContext.run()
        this.redissonClient = beanContext.getBean(RedissonClient.class);
    }
    void start(Promise<Void> startPromise) {
        vertx.setPeriodic(1000, this::procesCustomerInStreamingQueue)
        startPromise.complete()
    }

    void procesCustomerInStreamingQueue(Long id) {
        Queue<Customer> streamingQueue = redissonClient.getQueue(STREAMING_QUEUE)
        Customer customer = streamingQueue.poll()
        if(customer) {
            Vertx.vertx().eventBus().publish(CUSTOMER_PROCESS_EVENT_BUS, this::createPayload)
        }
    }

    JsonObject createPayload(Customer customer) {
        new JsonObject()
            .put("id", customer.id)
            .put("name", customer.name)
            .put("email", customer.email)
    }

    void stop() {
        println("Closing the jedispool")
    }
}
