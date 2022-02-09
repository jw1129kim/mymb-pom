package com.mymb.platform.api.repositories;

import com.mymb.platform.api.model.user.UserStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserStatusRepository extends MongoRepository<UserStatus, String> {
    UserStatus findByName(String name);
}
