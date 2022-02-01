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
@JsonDeserialize(builder = UserStatus.UserStatusBuilder.class)
@Builder(toBuilder = true)
@Document(collection = "userStatus")
public class UserStatus {

    @JsonProperty
    private String _id;

    @JsonProperty
    private String name;

    @JsonProperty
    private long delta;

    @JsonProperty
    private long totalUser;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserStatusBuilder {}
}
