package bz.dcr.bedrock.common.db;

import bz.dcr.bedrock.common.pubsub.BedRockSubscriber;
import bz.dcr.bedrock.common.pubsub.Message;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Redis implements Closeable {

    private JedisPoolConfig jedisPoolConfig;
    private JedisPool jedisPool;

    private ExecutorService executorService;


    public Redis(JedisPoolConfig jedisPoolConfig) {
        executorService = Executors.newCachedThreadPool();
        this.jedisPoolConfig = jedisPoolConfig;
    }


    public void connect(String host, int port, int timeout) {
        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
    }

    public void connect(String host, int port, int timeout, String password) {
        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
    }

    public void close() throws IOException {
        if(jedisPool != null)
            jedisPool.close();
    }


    public Jedis getResource() {
        return jedisPool.getResource();
    }


    public void publish(String channel, Message<?> message) {
        try (Jedis jedis = getResource()) {
            jedis.publish(channel, message.toString());
        }
    }

    public void publish(Message<?> message) {
        publish(message.getHeader().getChannel(), message);
    }

    public void subscribe(BedRockSubscriber subscriber, String... channels) {
        executorService.submit(() -> getResource().subscribe(subscriber, channels));
    }

}
