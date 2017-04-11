package bz.dcr.bedrock.common.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class Message<T> {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(new MessageSerializer(Message.class));
        module.addDeserializer(Message.class, new MessageDeserializer(Message.class));
        objectMapper.registerModule(module);
    }


    private MessageHeader header;
    private T body;


    public Message() {
        this(null, null);
    }

    public Message(MessageHeader header, T body) {
        this.header = header;
        this.body = body;
    }


    public MessageHeader getHeader() {
        return header;
    }

    public void setHeader(MessageHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }


    @Override
    public String toString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }


    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
