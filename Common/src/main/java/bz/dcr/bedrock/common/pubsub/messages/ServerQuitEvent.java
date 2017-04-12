package bz.dcr.bedrock.common.pubsub.messages;

import java.util.Date;
import java.util.UUID;

public class ServerQuitEvent {

    private UUID playerId;
    private Date quitTime;


    public ServerQuitEvent() {
    }

    public ServerQuitEvent(UUID playerId, Date quitTime) {
        this.playerId = playerId;
        this.quitTime = quitTime;
    }


    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public Date getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(Date quitTime) {
        this.quitTime = quitTime;
    }

}
