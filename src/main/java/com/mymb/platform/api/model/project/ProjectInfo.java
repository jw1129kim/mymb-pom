package com.mymb.platform.api.model.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(builder = ProjectInfo.ProjectInfoBuilder.class)
@Builder(toBuilder = true)
@Document(collection = "projects")
public class ProjectInfo {

    @JsonProperty
    private String _id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String amount;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProjectInfoBuilder {}
}
