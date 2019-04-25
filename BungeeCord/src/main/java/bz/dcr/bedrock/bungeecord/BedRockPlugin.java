package bz.dcr.bedrock.bungeecord;

import bz.dcr.bedrock.bungeecord.listener.JoinQuitListener;
import bz.dcr.bedrock.bungeecord.utils.ConfigUtil;
import bz.dcr.bedrock.common.config.ConfigKey;
import bz.dcr.bedrock.common.db.Redis;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.io.IOException;

public class BedRockPlugin extends Plugin {

    private static BedRockPlugin plugin;

    private Configuration config;
    private Redis redis;


    @Override
    public void onEnable() {
        plugin = this;

        // Load config
        try {
            loadConfig();
        } catch (IOException e) {
            getLogger().warning("Failed to load config!");
            e.printStackTrace();
            return;
        }

        initRedis();
        registerListeners();
    }

    @Override
    public void onDisable() {
        try {
            redis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadConfig() throws IOException {
        final File configFile = new File(getDataFolder(), "config.yml");

        config = ConfigUtil.loadConfig(configFile, getResourceAsStream("config.yml"));
    }

    private void initRedis() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(2);
        poolConfig.setMaxTotal(16);

        redis = new Redis(poolConfig);

        if (config.getBoolean(ConfigKey.REDIS_AUTH_ENABLED)) {
            redis.connect(
                    config.getString(ConfigKey.REDIS_HOST),
                    config.getInt(ConfigKey.REDIS_PORT),
                    config.getInt(ConfigKey.REDIS_TIMEOUT),
                    config.getString(ConfigKey.REDIS_AUTH_PASSWORD)
            );
        } else {
            redis.connect(
                    config.getString(ConfigKey.REDIS_HOST),
                    config.getInt(ConfigKey.REDIS_PORT),
                    config.getInt(ConfigKey.REDIS_TIMEOUT)
            );
        }
    }

    private void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new JoinQuitListener(this));
    }


    public Redis getRedis() {
        return redis;
    }

    public Configuration getConfig() {
        return config;
    }


    public static BedRockPlugin getPlugin() {
        return plugin;
    }

}
