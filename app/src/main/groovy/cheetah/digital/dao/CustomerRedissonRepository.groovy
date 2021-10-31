package cheetah.digital.dao

import cheetah.digital.model.Customer
import groovy.transform.CompileStatic
import jakarta.inject.Inject
import jakarta.inject.Singleton
import static cheetah.digital.constants.DB.*
import org.redisson.api.RQueue
import org.redisson.api.RedissonClient

@Singleton
@CompileStatic
class CustomerRedissonRepository implements Repository<Customer> {


    private final RedissonClient redissonClient
    private final CustomerBloomFilterRepository customerBloomFilterRepository

    @Inject
    CustomerRedissonRepository(RedissonClient redissonClient, CustomerBloomFilterRepository customerBloomFilterRepository) {
        this.redissonClient = redissonClient
        this.customerBloomFilterRepository = customerBloomFilterRepository
    }

    @Override
    void insert(Customer customer) {
        RQueue<Customer> customerQueue = redissonClient.getQueue(CUSTOMER_QUEUE)
        customerQueue.add(customer)
        if(!customerBloomFilterRepository.exists(customer) ) {
            customerBloomFilterRepository.addObject(customer)
        }
    }

    RedissonClient getRedissonClient() {
        return this.redissonClient
    }
}
