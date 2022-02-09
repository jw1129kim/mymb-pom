package com.mymb.platform.api.support;

import com.mymb.platform.api.model.project.ProjectInfoResult;
import lombok.Builder;

import java.util.List;

public class ProjectResponse extends APIResponse<ProjectInfoResult> {

    @Builder
    public ProjectResponse(final ProjectInfoResult projectInfoResult, final List<String> errors){
        super(projectInfoResult);
        this.setErrors(errors);

    }
}
