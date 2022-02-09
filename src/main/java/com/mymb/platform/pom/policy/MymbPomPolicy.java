package com.mymb.platform.pom.policy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = MymbPomPolicy.MymbPomPolicyBuilder.class)
@Builder(toBuilder = true)
public class MymbPomPolicy {

    @JsonProperty
    private String name;

    @JsonProperty
    private long issueConditionByProjects;

    @JsonProperty
    private int numOfTokenByProjects;

    @JsonProperty
    private int issueConditionByUsers;

    @JsonProperty
    private int numOfTokenByUsers;

    @JsonPOJOBuilder(withPrefix = "")
    public static class MymbPomPolicyBuilder {}

}



