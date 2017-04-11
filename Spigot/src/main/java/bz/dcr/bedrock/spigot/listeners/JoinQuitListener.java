package bz.dcr.bedrock.spigot.listeners;

import bz.dcr.bedrock.common.config.ConfigKey;
import bz.dcr.bedrock.common.pubsub.Message;
import bz.dcr.bedrock.common.pubsub.MessageHeader;
import bz.dcr.bedrock.common.pubsub.MessageTargetType;
import bz.dcr.bedrock.common.pubsub.PubSubChannel;
import bz.dcr.bedrock.common.pubsub.messages.JoinEventMessageBody;
import bz.dcr.bedrock.common.pubsub.messages.QuitEventMessageBody;
import bz.dcr.bedrock.spigot.BedRockPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.HashSet;

public class JoinQuitListener implements Listener {

    private BedRockPlugin plugin;


    public JoinQuitListener(BedRockPlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        MessageHeader header = new MessageHeader(
                JoinEventMessageBody.class,
                PubSubChannel.JOIN_QUIT,
                plugin.getConfig().getString(ConfigKey.GENERAL_BUNGEECORD_SERVER_NAME),
                MessageTargetType.GLOBAL,
                new HashSet<>()
        );
        JoinEventMessageBody body = new JoinEventMessageBody(
                event.getPlayer().getUniqueId(),
                new Date()
        );
        Message<JoinEventMessageBody> message = new Message<>(header, body);

        // Publish message
        plugin.getRedis().publish(PubSubChannel.JOIN_QUIT, message);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        MessageHeader header = new MessageHeader(
                QuitEventMessageBody.class,
                PubSubChannel.JOIN_QUIT,
                plugin.getConfig().getString(ConfigKey.GENERAL_BUNGEECORD_SERVER_NAME),
                MessageTargetType.GLOBAL,
                new HashSet<>()
        );
        QuitEventMessageBody body = new QuitEventMessageBody(
                event.getPlayer().getUniqueId(),
                new Date()
        );
        Message<QuitEventMessageBody> message = new Message<>(header, body);

        // Publish message
        plugin.getRedis().publish(PubSubChannel.JOIN_QUIT, message);
    }

}
