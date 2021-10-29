package cheetah.digital.beans

import groovy.transform.CompileStatic
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import redis.clients.jedis.DefaultJedisClientConfig
import redis.clients.jedis.HostAndPort
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig

import java.time.Duration

@Factory
@CompileStatic
class BeanConfiguration {

    @Singleton
    JedisPool jedisPool() {
        final DefaultJedisClientConfig defaultJedisClientConfig =   DefaultJedisClientConfig
                .builder()//.user("adminuser").password("Kerrie_1988")
                .build()
        final JedisPoolConfig poolConfig = buildPoolConfig();
//        return new JedisPool(poolConfig, new HostAndPort("localhost", 6379), defaultJedisClientConfig);
        return new JedisPool(poolConfig, new HostAndPort("127.0.0.1", 6379), defaultJedisClientConfig);
    }

    @Singleton
    RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setUsername("admin")
                .setPassword("Kerrie_1988")
        return Redisson.create(config);

    }

    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig()
        poolConfig.setMaxTotal(128)
        poolConfig.setMaxIdle(128)
        poolConfig.setMinIdle(16)
        poolConfig.setTestOnBorrow(true)
        poolConfig.setTestOnReturn(true)
        poolConfig.setTestWhileIdle(true)
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis())
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis())
        poolConfig.setNumTestsPerEvictionRun(3)
        poolConfig.setBlockWhenExhausted(true)
        return poolConfig
    }

}
