package cheetah.digital.repository

import cheetah.digital.Customer
import jakarta.inject.Singleton
import jakarta.inject.Inject
import org.redisson.api.RedissonClient


@Singleton
class CustomerRepository implements BloomFilterRepository<Customer> {

    @Inject
    RedissonClient redissonClient;

    @Override
    boolean exists(Customer customer) {
        return false
    }

    @Override
    boolean addObject(Customer customer) {
        return false
    }

    @Override
    int count() {
        return 0
    }
}
