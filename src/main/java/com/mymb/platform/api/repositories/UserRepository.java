package com.mymb.platform.api.repositories;

import com.mymb.platform.api.model.user.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserInfo, String> {
    UserInfo findByName(String name);
}
