package com.gmt.gateway.response;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

@JsonSerialize(using = LoginResponse.LoginResponseSerializer.class)
public record LoginResponse(
    UserDetails userDetails,
    String signature,
    long timestamp,
    String token
) {

    public static LoginResponse smsResponse(String signature, long timestamp) {
        return new LoginResponse(null, signature, timestamp, "");
    }

    public static LoginResponse userDetailsResponse(UserDetails user, String signature, long timestamp, String token) {
        return new LoginResponse(user, signature, timestamp, token);
    }

    static class LoginResponseSerializer extends JsonSerializer<LoginResponse> {

        @Override
        public void serialize(LoginResponse value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();

            gen.writeObjectField("user", value.userDetails);
            gen.writeStringField("signature", value.signature);
            gen.writeNumberField("timestamp", value.timestamp);
            gen.writeStringField("Auth-Token", value.token);

            gen.writeEndObject();
        }
    }
}
