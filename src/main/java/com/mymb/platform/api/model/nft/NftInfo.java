package com.mymb.platform.api.model.nft;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = NftInfo.NftInfoBuilder.class)
@Builder(toBuilder = true)
public class NftInfo {

    @JsonProperty
    private String id;

    @JsonProperty
    private String uri;

    @JsonProperty
    private long amount;

    @JsonPOJOBuilder(withPrefix = "")
    public static class NftInfoBuilder {}
}
