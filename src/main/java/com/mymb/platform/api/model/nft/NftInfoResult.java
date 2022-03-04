package com.mymb.platform.api.model.nft;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = NftInfoResult.NftInfoResultBuilder.class)
@Builder(toBuilder = true)
@Document(collection = "nft")
public class NftInfoResult {

    @JsonProperty
    private String _id;

    @JsonProperty
    private String tokenId;

    @JsonProperty
    private String owner;

    @JsonProperty
    private String tokenURI;

    @JsonPOJOBuilder(withPrefix = "")
    public static class NftInfoResultBuilder {}
}
