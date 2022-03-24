package com.skyestock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConfig {

    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;

    @Value("${spring.redis.strategy}")
    private String strategy;

    @Autowired
    RedisProperties redisProperties;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(redisProperties.getJedis().getPool() == null ? 100 : redisProperties.getJedis().getPool().getMaxIdle());
        //最小空闲连接数
        jedisPoolConfig.setMinIdle(redisProperties.getJedis().getPool() == null ? 20 : redisProperties.getJedis().getPool().getMinIdle());
        //当池内没有可用连接时，最大等待时间
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getJedis().getPool() == null ? 10000 : redisProperties.getJedis().getPool().getMaxWait().toMillis());

        return jedisPoolConfig;
    }

    //Redis 单点
    @Bean
    @ConditionalOnProperty(name="spring.redis.strategy", havingValue = "1")
    public JedisConnectionFactory getRedisFactory(JedisPoolConfig jedisPoolConfig) {

        if(redisProperties.getCluster().getNodes().size() > 1) {
            return null;
        }

        String[] split1 = redisProperties.getCluster().getNodes().get(0).split(":");

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(split1[0], Integer.parseInt(split1[1]));
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            configuration.setPassword(RedisPassword.of(redisProperties.getPassword()));
            configuration.setDatabase(redisProperties.getDatabase());
        }
        //获得默认的连接池构造
        //这里需要注意的是，edisConnectionFactoryJ对于Standalone模式的没有（RedisStandaloneConfiguration，JedisPoolConfig）的构造函数，对此
        //我们用JedisClientConfiguration接口的builder方法实例化一个构造器，还得类型转换
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcf = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        //修改我们的连接池配置
        jpcf.poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcf.build();
        JedisConnectionFactory factory = new JedisConnectionFactory(configuration, jedisClientConfiguration);
        return factory;
    }

    //Redis 集群配置
    @Bean
    @ConditionalOnProperty(name="spring.redis.strategy", havingValue = "2")
    public JedisConnectionFactory getClusterRedisFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory;
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        for (String  s :redisProperties.getCluster().getNodes()) {
            String[] split1 = s.split(":");
            redisClusterConfiguration.addClusterNode(new RedisNode(split1[0], Integer.parseInt(split1[1])));
        }
        //集群时最大重定向个数
        redisClusterConfiguration.setMaxRedirects(5);
        jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig );
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        }
        return jedisConnectionFactory;
    }


}
