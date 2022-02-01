package com.mymb.platform.api.model.mymb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = MymbTokenResult.MymbTokenResultBuilder.class)
@Builder(toBuilder = true)
public class MymbTokenResult {

    @JsonProperty
    public int totalAmountOfToken;

    @JsonPOJOBuilder(withPrefix = "")
    public static class MymbTokenResultBuilder {}
}
