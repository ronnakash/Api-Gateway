package com.gmt.gateway.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CoreLoginResponse { //TODO: add error code and check it

    private final String smsCode;
    private final String authToken;
    private final UserDetails userDetails;

    @JsonCreator
    public CoreLoginResponse(
            @JsonProperty("smsCode") String smsCode,
            @JsonProperty("Auth-Token") String authToken,
            @JsonProperty("userDetails") UserDetails userDetails
    ) {
        this.smsCode = smsCode;
        this.authToken = authToken;
        this.userDetails = userDetails;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public String getAuthToken() {
        return authToken;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}
