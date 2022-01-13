package com.mymb.platform.api.model.nft;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = NftInfoResult.NftInfoResultBuilder.class)
@Builder(toBuilder = true)
public class NftInfoResult {

    @JsonProperty
    private String tokenId;

    @JsonProperty
    private String owner;

    @JsonProperty
    private String tokenURI;

    @JsonPOJOBuilder(withPrefix = "")
    public static class NftInfoResultBuilder {}
}
