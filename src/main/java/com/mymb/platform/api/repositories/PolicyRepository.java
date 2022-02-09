package com.mymb.platform.api.repositories;

import com.mymb.platform.api.model.policy.MymbPolicy;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PolicyRepository extends MongoRepository<MymbPolicy, String> {
    MymbPolicy findByName(String name);
}
