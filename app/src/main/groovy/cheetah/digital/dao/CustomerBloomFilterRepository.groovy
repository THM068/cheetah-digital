package cheetah.digital.dao

import cheetah.digital.model.Customer
import groovy.transform.CompileStatic
import jakarta.inject.Singleton
import jakarta.inject.Inject
import org.redisson.api.RBloomFilter
import org.redisson.api.RedissonClient

@Singleton
@CompileStatic
class CustomerBloomFilterRepository implements BloomFilterRepository<Customer> {

    final static String CUSTOMER_REPO_KEY_QUEUE="customer-repo-bloomfilter"
    final RedissonClient redissonClient
    final RBloomFilter<Customer> customerRBloomFilter

    @Inject
    CustomerBloomFilterRepository(RedissonClient redissonClient) {
        this.redissonClient = redissonClient
        this.customerRBloomFilter = this.redissonClient.getBloomFilter(CUSTOMER_REPO_KEY_QUEUE)
        this.customerRBloomFilter.tryInit(1000000L, 0.03d);
    }

    @Override
    boolean exists(Customer customer) {
        return this.customerRBloomFilter.contains(customer)
    }

    @Override
    boolean addObject(Customer customer) {
        return this.customerRBloomFilter.add(customer)
    }

    @Override
    long count() {
        return this.customerRBloomFilter.count()
    }
}
