package com.mymb.platform.api.model.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = ProjectInfoResult.ProjectInfoResultBuilder.class)
@Builder(toBuilder = true)
public class ProjectInfoResult {

    @JsonProperty
    private String id;

    @JsonProperty
    private String uri;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProjectInfoResultBuilder {}
}
