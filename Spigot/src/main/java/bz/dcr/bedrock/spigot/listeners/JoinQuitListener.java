package bz.dcr.bedrock.spigot.listeners;

import bz.dcr.bedrock.common.config.ConfigKey;
import bz.dcr.bedrock.common.pubsub.*;
import bz.dcr.bedrock.common.pubsub.messages.ServerJoinEvent;
import bz.dcr.bedrock.common.pubsub.messages.ServerQuitEvent;
import bz.dcr.bedrock.spigot.BedRockPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;

public class JoinQuitListener implements Listener {

    private BedRockPlugin plugin;


    public JoinQuitListener(BedRockPlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ServerJoinEvent body = new ServerJoinEvent(
                event.getPlayer().getUniqueId(),
                new Date()
        );

        MessageBuilder<ServerJoinEvent> builder = new MessageBuilder<>();
        Message<ServerJoinEvent> message = builder
                .type(ServerJoinEvent.class)
                .channel(MessageChannel.JOIN_QUIT)
                .source(plugin.getConfig().getString(ConfigKey.GENERAL_BUNGEECORD_SERVER_NAME))
                .global()
                .body(body)
                .build();

        // Publish message
        plugin.getRedis().publish(MessageChannel.JOIN_QUIT, message);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ServerQuitEvent body = new ServerQuitEvent(
                event.getPlayer().getUniqueId(),
                new Date()
        );

        MessageBuilder<ServerQuitEvent> builder = new MessageBuilder<>();
        Message<ServerQuitEvent> message = builder
                .type(ServerQuitEvent.class)
                .channel(MessageChannel.JOIN_QUIT)
                .source(plugin.getConfig().getString(ConfigKey.GENERAL_BUNGEECORD_SERVER_NAME))
                .global()
                .body(body)
                .build();

        // Publish message
        plugin.getRedis().publish(MessageChannel.JOIN_QUIT, message);
    }

}
