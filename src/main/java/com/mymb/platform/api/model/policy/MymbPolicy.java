package com.mymb.platform.api.model.policy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.mymb.platform.api.model.project.ProjectInfo;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = MymbPolicy.MymbPolicyBuilder.class)
@Builder(toBuilder = true)
@Document(collection = "policy")
public class MymbPolicy {

    @JsonProperty
    private String _id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String content;

    @JsonPOJOBuilder(withPrefix = "")
    public static class MymbPolicyBuilder {}
}
