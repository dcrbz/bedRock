package bz.dcr.bedrock.common.pubsub;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class MessageSerializer extends StdSerializer<Message> {

    public MessageSerializer(Class<Message> t) {
        super(t);
    }


    @Override
    public void serialize(Message message, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject(); // Parent object

        gen.writeFieldName("header");
        gen.writeStartObject(); // Header object
        gen.writeStringField("type", message.getHeader().getType().getName());
        gen.writeStringField("channel", message.getHeader().getChannel());
        gen.writeStringField("source", message.getHeader().getSource());
        gen.writeStringField("targetType", message.getHeader().getTargetType().name());
        gen.writeFieldName("targets");
        gen.writeStartArray();
        for(String target : message.getHeader().getTargets()) {
            gen.writeString(target);
        }
        gen.writeEndArray();
        gen.writeEndObject(); // Header object

        gen.writeObjectField("body", message.getBody()); // Body object
        gen.writeEndObject(); // Parent object
    }

}
