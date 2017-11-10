package bz.dcr.bedrock.bungeecord.listener;

import bz.dcr.bedrock.bungeecord.BedRockPlugin;
import bz.dcr.bedrock.common.pubsub.Message;
import bz.dcr.bedrock.common.pubsub.MessageBuilder;
import bz.dcr.bedrock.common.pubsub.MessageChannel;
import bz.dcr.bedrock.common.pubsub.messages.BungeeCordJoinEvent;
import bz.dcr.bedrock.common.pubsub.messages.BungeeCordQuitEvent;
import bz.dcr.bedrock.common.pubsub.messages.ServerJoinEvent;
import bz.dcr.bedrock.common.pubsub.messages.ServerQuitEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Date;

public class JoinQuitListener implements Listener {

    private BedRockPlugin plugin;


    public JoinQuitListener(BedRockPlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PostLoginEvent event) {
        final BungeeCordJoinEvent body = new BungeeCordJoinEvent(event.getPlayer().getUniqueId(), new Date());
        Message<BungeeCordJoinEvent> message = new MessageBuilder<BungeeCordJoinEvent>()
                .type(BungeeCordJoinEvent.class)
                .channel(MessageChannel.BUNGEECORD_JOIN_QUIT)
                .source(plugin.getConfig().getString("Server-ID"))
                .global()
                .body(body)
                .build();

        plugin.getRedis().publish(message);
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        final BungeeCordQuitEvent body = new BungeeCordQuitEvent(event.getPlayer().getUniqueId(), new Date());
        Message<BungeeCordQuitEvent> message = new MessageBuilder<BungeeCordQuitEvent>()
                .type(BungeeCordQuitEvent.class)
                .channel(MessageChannel.BUNGEECORD_JOIN_QUIT)
                .source(plugin.getConfig().getString("Server-ID"))
                .global()
                .body(body)
                .build();

        plugin.getRedis().publish(message);
    }


    @EventHandler
    public void onServerJoin(ServerConnectedEvent event) {
        final ServerJoinEvent body = new ServerJoinEvent(event.getPlayer().getUniqueId(), new Date());
        Message<ServerJoinEvent> message = new MessageBuilder<ServerJoinEvent>()
                .type(ServerJoinEvent.class)
                .channel(MessageChannel.SERVER_JOIN_QUIT)
                .source(plugin.getConfig().getString("Server-ID"))
                .global()
                .body(body)
                .build();

        plugin.getRedis().publish(message);
    }

    @EventHandler
    public void onServerQuit(ServerDisconnectEvent event) {
        final ServerQuitEvent body = new ServerQuitEvent(event.getPlayer().getUniqueId(), new Date());
        Message<ServerQuitEvent> message = new MessageBuilder<ServerQuitEvent>()
                .type(ServerQuitEvent.class)
                .channel(MessageChannel.SERVER_JOIN_QUIT)
                .source(plugin.getConfig().getString("Server-ID"))
                .global()
                .body(body)
                .build();

        plugin.getRedis().publish(message);
    }

}
