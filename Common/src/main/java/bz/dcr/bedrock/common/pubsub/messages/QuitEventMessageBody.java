package bz.dcr.bedrock.common.pubsub.messages;

import java.util.Date;
import java.util.UUID;

public class QuitEventMessageBody {

    private UUID playerId;
    private Date quitTime;


    public QuitEventMessageBody() {
    }

    public QuitEventMessageBody(UUID playerId, Date quitTime) {
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
