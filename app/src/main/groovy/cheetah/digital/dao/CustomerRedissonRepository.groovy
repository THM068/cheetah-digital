package cheetah.digital.dao

import cheetah.digital.dao.utils.RedisSchema
import cheetah.digital.model.Customer
import groovy.transform.CompileStatic
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.redisson.api.RMap
import org.redisson.api.RedissonClient

@Singleton
@CompileStatic
class CustomerRedissonRepository implements Repository<Customer> {

    static String CUSTOMER_MAP="customer-info-map"
    private final RedissonClient redissonClient
    private final CustomerBloomFilterRepository customerBloomFilterRepository

    @Inject
    CustomerRedissonRepository(RedissonClient redissonClient, CustomerBloomFilterRepository customerBloomFilterRepository) {
        this.redissonClient = redissonClient
        this.customerBloomFilterRepository = customerBloomFilterRepository
    }

    @Override
    void insert(Customer customer) {
        String hashKey = RedisSchema.getCustomerHashKey(customer.id)
        RMap<String,Customer> customerMap = redissonClient.getMap(CUSTOMER_MAP)
        customerMap.putIfAbsent(hashKey, customer)
        if(!customerBloomFilterRepository.exists(customer) ) {
            customerBloomFilterRepository.addObject(customer)
        }
    }
}
