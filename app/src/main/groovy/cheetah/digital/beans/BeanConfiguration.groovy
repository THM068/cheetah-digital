package cheetah.digital.beans

import groovy.transform.CompileStatic
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config

@Factory
@CompileStatic
class BeanConfiguration {

    @Singleton
    RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setConnectionPoolSize(100)
//                .setUsername("admin")
//                .setPassword("Kerrie_1988")
        return Redisson.create(config);
    }
}
