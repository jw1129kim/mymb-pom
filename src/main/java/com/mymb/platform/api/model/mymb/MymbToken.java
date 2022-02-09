package com.mymb.platform.api.model.mymb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = MymbToken.MymbTokenBuilder.class)
@Builder(toBuilder = true)
public class MymbToken {

    @JsonProperty
    public int amountOfToken;

    @JsonPOJOBuilder(withPrefix = "")
    public static class MymbTokenResultBuilder {}
}
