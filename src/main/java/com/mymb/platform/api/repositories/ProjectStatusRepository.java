package com.mymb.platform.api.repositories;

import com.mymb.platform.api.model.project.ProjectStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectStatusRepository extends MongoRepository<ProjectStatus, String> {
    ProjectStatus findByName(String name);
}
