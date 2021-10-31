/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package cheetah.digital

import cheetah.digital.dao.CustomerRedissonRepository
import cheetah.digital.model.Customer
import cheetah.digital.verticles.MainVerticle
import groovy.transform.CompileStatic
import io.micronaut.context.BeanContext
import io.vertx.core.AsyncResult
import io.vertx.core.Vertx

import java.util.logging.Logger

@CompileStatic
class App {
    static Logger logger = Logger.getLogger("App")
    String getGreeting() {
        return 'Hello World!'
    }

    static void main(String[] args) {
        loadData()
        Vertx.vertx().deployVerticle(new MainVerticle(), App::getHandleDeployment)
    }

    static void getHandleDeployment(AsyncResult<String> handler) {
        if(handler.succeeded()) {
            logger.info("Deployed verticle ${handler.result()}")
        }
    }

    static List<Customer> customerList() {
        [
                new Customer(id: "121", name: "Thomas", email: "thomas@email.com"),
                new Customer(id: "125", name: "Alex", email: "alex@email.com"),
                new Customer(id: "126", name: "Jemima", email: "Jemima@email.com"),
                new Customer(id: "127", name: "Lorraine", email: "Lorraine@email.com"),
                new Customer(id: "128", name: "Constance", email: "Constance@email.com"),
                new Customer(id: "129", name: "Gugu", email: "Gugu@email.com"),
                new Customer(id: "130", name: "Khulani", email: "Khulani@email.com"),
                new Customer(id: "131", name: "Khaya", email: "Khaya@email.com"),
                new Customer(id: "132", name: "Thabo", email: "Thabo@email.com"),
        ]
    }

    static loadData() {
        BeanContext beanContext = BeanContext.run()
        CustomerRedissonRepository customerRedissonRepository = beanContext.getBean(CustomerRedissonRepository.class)

        customerList().each {customer ->
            customerRedissonRepository.insert(customer);
        }
        logger.info("Completed loading customer data ...")
    }
}
