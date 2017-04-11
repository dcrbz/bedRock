package bz.dcr.bedrock.common.pubsub;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashSet;

public class MessageDeserializer extends StdDeserializer<Message> {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public MessageDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public Message deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        Message message = new Message<>();
        MessageHeader header = new MessageHeader();

        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode headerNode = node.get("header");
        JsonNode bodyNode = node.get("body");

        try {
            header.setType(Class.forName(headerNode.get("type").asText()));
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        header.setChannel(headerNode.get("channel").asText());
        header.setSource(headerNode.get("source").asText());
        header.setTargetType(MessageTargetType.valueOf(headerNode.get("targetType").asText()));
        header.setTargets(new HashSet<>(headerNode.findValuesAsText("targets")));

        message.setHeader(header);
        message.setBody(objectMapper.treeToValue(bodyNode, message.getHeader().getType()));

        return message;
    }

}
