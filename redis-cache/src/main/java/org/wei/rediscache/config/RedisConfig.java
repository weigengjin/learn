package org.wei.rediscache.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.wei.rediscache.domain.Person;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int redisPoolMaxActive;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private int redisPoolMaxWait;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int redisPoolMaxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int redisPoolMinIdle;


    /**
     * 创建redis连接工厂
     */
    private JedisConnectionFactory createJedisConnectionFactory(String host, int port) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisPoolMaxIdle);
        jedisPoolConfig.setMinIdle(redisPoolMinIdle);
        jedisPoolConfig.setMaxWaitMillis(redisPoolMaxWait);
        jedisPoolConfig.setMaxTotal(redisPoolMaxActive);
        JedisClientConfiguration clientConfiguration = JedisClientConfiguration.builder().usePooling()
            .poolConfig(jedisPoolConfig).build();
        return new JedisConnectionFactory(configuration, clientConfiguration);
    }

    private RedisTemplate<String, Person> createStringRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Person> template = new RedisTemplate<>();
        Jackson2JsonRedisSerializer<Person> serializer = new Jackson2JsonRedisSerializer<>(Person.class);
        template.setConnectionFactory(factory);
        template.setKeySerializer(serializer);
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(serializer);
        template.setHashValueSerializer(serializer);
        return template;
    }

    @Bean(name = "personRedisConnectionFactory")
    public RedisConnectionFactory recallSortRedisConnectionFactory() {
        return createJedisConnectionFactory(host, port);
    }

    @Bean(name = "personRedisTemplate")
    public RedisTemplate<String, Person> recallSortRedisTemplate(@Qualifier("personRedisConnectionFactory") RedisConnectionFactory factory) {
        return createStringRedisTemplate(factory);
    }


}
