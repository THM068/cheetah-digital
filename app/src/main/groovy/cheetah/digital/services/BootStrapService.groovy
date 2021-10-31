package cheetah.digital.services

import cheetah.digital.dao.CustomerRedissonRepository
import cheetah.digital.model.Customer
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.redisson.api.RQueue
import org.redisson.api.RedissonClient

import static cheetah.digital.constants.DB.getCUSTOMER_QUEUE


@Singleton
class BootStrapService {

    private final CustomerRedissonRepository customerRedissonRepository
    static final String STREAMING_QUEUE = "customer-streaming-queue"

    @Inject
    BootStrapService(CustomerRedissonRepository customerRedissonRepository) {
        this.customerRedissonRepository = customerRedissonRepository
    }

    void loadData() {
        customerList().each {customer ->
            customerRedissonRepository.insert(customer);
        }

        RedissonClient redissonClient = customerRedissonRepository.redissonClient
        RQueue streamingQueue = redissonClient.getQueue(STREAMING_QUEUE)
        streamingQueue.clear()
        streamingQueue.addAll(streamingQueueList())
    }

    private List<Customer> customerList() {
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

    private List<Customer> streamingQueueList() {
        [
                new Customer(id: "127", name: "Lorraine", email: "Lorraine@email.com"),
                new Customer(id: "400", name: "Pan cake", email: "pan.cake@email.com"),
                new Customer(id: "130", name: "Khulani", email: "Khulani@email.com"),
                new Customer(id: "400", name: "Pan cake", email: "pan.cake@email.com"),
                new Customer(id: "401", name: "Pan cake", email: "pan.401@email.com"),
                new Customer(id: "402", name: "402Pan cake", email: "pan.402@email.com"),
                new Customer(id: "403", name: "Pan403cake", email: "pan.401@email.com"),
                new Customer(id: "405", name: "Pan405 cake", email: "pan.405@email.com"),
                new Customer(id: "403", name: "Pan cake403", email: "pan.403@email.com"),
                new Customer(id: "404", name: "Pan cake404", email: "pan.404@email.com"),
                new Customer(id: "125", name: "Alex", email: "alex@email.com"),
        ]
    }
}
