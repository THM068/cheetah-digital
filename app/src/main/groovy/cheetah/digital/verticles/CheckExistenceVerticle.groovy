package cheetah.digital.verticles

import cheetah.digital.constants.DB
import cheetah.digital.dao.CustomerBloomFilterRepository
import cheetah.digital.model.Customer
import io.micronaut.context.BeanContext
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject

import java.util.logging.Logger

class CheckExistenceVerticle extends AbstractVerticle {
    static Logger logger = Logger.getLogger("CheckExistenceVerticle")
    final CustomerBloomFilterRepository customerBloomFilterRepository

    CheckExistenceVerticle() {
        BeanContext context = BeanContext.run()
        this.customerBloomFilterRepository = context.getBean(CustomerBloomFilterRepository.class)
    }

    void start() {
        vertx.eventBus().<JsonObject>consumer(DB.CUSTOMER_PROCESS_EVENT_BUS, this::processCustomer)
    }

    private void processCustomer(Message<JsonObject> handler) {
        logger.info("*****************")
        JsonObject jsonObject = handler.body()
        Customer customer = createCustomer(jsonObject)
        if(customerBloomFilterRepository.exists(customer)) {
            logger.info("customer ${customer}  exists in the database")
        }
        else {
            logger.info("customer ${customer}  does not exist in the database")
        }
        logger.info("*****************")
    }

    void stop(Promise<Void> startPromise) {
        logger.info("Stopping CheckExistenceVerticle ...")
    }

    private Customer createCustomer(JsonObject jsonObject) {
        new Customer(
          id: jsonObject.getString("id"),
          name: jsonObject.getString("name"),
          email: jsonObject.getString("email"),
        )
    }
}
