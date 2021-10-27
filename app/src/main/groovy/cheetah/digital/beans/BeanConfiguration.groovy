package cheetah.digital.beans

import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.api.RedissonReactiveClient
import org.redisson.config.Config

@Factory
class BeanConfiguration {

    @Singleton
    RedissonClient redissonReactiveClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://redis-17035.c6.eu-west-1-1.ec2.cloud.redislabs.com:17035")
                .setUsername("admin")
                .setPassword("Kerrie_1988")
        return Redisson.create(config);

    }
}
