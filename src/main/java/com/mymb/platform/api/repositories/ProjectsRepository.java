package com.mymb.platform.api.repositories;

import com.mymb.platform.api.model.project.ProjectInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectsRepository extends MongoRepository<ProjectInfo, String> {
    ProjectInfo findByName(String name);
}
