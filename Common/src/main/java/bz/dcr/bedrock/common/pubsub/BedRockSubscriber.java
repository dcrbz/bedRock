package bz.dcr.bedrock.common.pubsub;

import redis.clients.jedis.JedisPubSub;

import java.io.IOException;

public abstract class BedRockSubscriber<T> extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        try {
            Message<T> msg = Message.getObjectMapper().readValue(message, Message.class);
            onMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public abstract void onMessage(Message<T> message);

}
