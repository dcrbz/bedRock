package bz.dcr.bedrock.common.pubsub;

import java.util.HashSet;
import java.util.Set;

public class MessageHeader {

    private Class type;
    private String channel;
    private String source;
    private MessageTargetType targetType;
    private Set<String> targets;


    public MessageHeader() {
        targets = new HashSet<>();
    }

    public MessageHeader(Class type, String channel, String source, MessageTargetType targetType, Set<String> targets) {
        this.type = type;
        this.channel = channel;
        this.source = source;
        this.targetType = targetType;
        this.targets = targets;
    }


    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public MessageTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(MessageTargetType targetType) {
        this.targetType = targetType;
    }

    public Set<String> getTargets() {
        return targets;
    }

    public void setTargets(Set<String> targets) {
        this.targets = targets;
    }

    public boolean addTarget(String target) {
        return targets.add(target);
    }

    public boolean removeTarget(String target) {
        return targets.remove(target);
    }

    public boolean hasTarget(String target) {
        return targets.contains(target);
    }

}
