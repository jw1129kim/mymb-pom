package com.mymb.platform.api.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = UserInfo.UserInfoBuilder.class)
@Builder(toBuilder = true)
@Document(collection = "users")
public class UserInfo {

    @JsonProperty
    private String _id;

    @JsonProperty
    private String name;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserInfoBuilder {}
}
