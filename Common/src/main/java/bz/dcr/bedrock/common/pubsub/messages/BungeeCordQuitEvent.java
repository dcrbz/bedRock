package bz.dcr.bedrock.common.pubsub.messages;

import java.util.Date;
import java.util.UUID;

public class BungeeCordQuitEvent {

    private UUID playerId;
    private Date joinTime;


    public BungeeCordQuitEvent() {
    }

    public BungeeCordQuitEvent(UUID playerId, Date joinTime) {
        this.playerId = playerId;
        this.joinTime = joinTime;
    }


    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

}
