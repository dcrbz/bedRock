package bz.dcr.bedrock.spigot;

import bz.dcr.bedrock.common.config.ConfigKey;
import bz.dcr.bedrock.common.db.Redis;
import bz.dcr.bedrock.common.pubsub.BedRockSubscriber;
import bz.dcr.bedrock.common.pubsub.Message;
import bz.dcr.bedrock.common.pubsub.PubSubChannel;
import bz.dcr.bedrock.common.pubsub.messages.JoinEventMessageBody;
import bz.dcr.bedrock.common.pubsub.messages.QuitEventMessageBody;
import bz.dcr.bedrock.spigot.listeners.JoinQuitListener;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

public class BedRockPlugin extends JavaPlugin {

    private static BedRockPlugin plugin;

    private Redis redis;


    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        initRedis();
        registerListeners();

        redis.subscribe(new BedRockSubscriber<JoinEventMessageBody>() {
            @Override
            public void onMessage(Message<JoinEventMessageBody> message) {
                JoinEventMessageBody body = message.getBody();

                System.out.println(body.getPlayerId().toString() + " joined the server '" + message.getHeader().getSource() + "'.");
            }
        }, PubSubChannel.JOIN_QUIT);

        redis.subscribe(new BedRockSubscriber<QuitEventMessageBody>() {
            @Override
            public void onMessage(Message<QuitEventMessageBody> message) {
                QuitEventMessageBody body = message.getBody();

                System.out.println(body.getPlayerId().toString() + " left the server '" + message.getHeader().getSource() + "'.");
            }
        }, PubSubChannel.JOIN_QUIT);
    }

    @Override
    public void onDisable() {
        try {
            redis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initRedis() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(2);
        poolConfig.setMaxTotal(16);

        redis = new Redis(poolConfig);

        if(getConfig().getBoolean(ConfigKey.REDIS_AUTH_ENABLED)) {
            redis.connect(
                    getConfig().getString(ConfigKey.REDIS_HOST),
                    getConfig().getInt(ConfigKey.REDIS_PORT),
                    getConfig().getInt(ConfigKey.REDIS_TIMEOUT),
                    getConfig().getString(ConfigKey.REDIS_AUTH_PASSWORD)
            );
        } else {
            redis.connect(
                    getConfig().getString(ConfigKey.REDIS_HOST),
                    getConfig().getInt(ConfigKey.REDIS_PORT),
                    getConfig().getInt(ConfigKey.REDIS_TIMEOUT)
            );
        }
    }


    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new JoinQuitListener(this), this);
    }


    public Redis getRedis() {
        return redis;
    }


    public static BedRockPlugin getPlugin() {
        return plugin;
    }

}
