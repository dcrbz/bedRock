package bz.dcr.bedrock.common.pubsub;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Helper class to build Messages
 * @param <T> The type of the message body
 */
public class MessageBuilder<T> {

    private MessageHeader header;
    private T body;


    public MessageBuilder() {
        header = new MessageHeader();
    }


    public MessageBuilder<T> type(Class type) {
        header.setType(type);
        return this;
    }

    public MessageBuilder<T> channel(String channel) {
        header.setChannel(channel);
        return this;
    }

    public MessageBuilder<T> source(String source) {
        header.setSource(source);
        return this;
    }

    public MessageBuilder<T> targetType(MessageTargetType targetType) {
        header.setTargetType(targetType);
        return this;
    }

    public MessageBuilder<T> targets(String... targets) {
        Set<String> targetSet = new HashSet<>();
        Collections.addAll(targetSet, targets);
        header.setTargets(targetSet);
        return this;
    }

    public MessageBuilder<T> targets(Set<String> targets) {
        header.setTargets(targets);
        return this;
    }

    public MessageBuilder<T> global() {
        header.setTargetType(MessageTargetType.GLOBAL);
        header.setTargets(new HashSet<>());
        return this;
    }

    public MessageBuilder<T> body(T body) {
        this.body = body;
        return this;
    }


    /**
     * Builds a message using the built header and body
     * @return A message containing the built header and body
     */
    public Message<T> build() {
        Message<T> message = new Message<>();
        message.setHeader(header);
        message.setBody(body);
        return message;
    }

}
