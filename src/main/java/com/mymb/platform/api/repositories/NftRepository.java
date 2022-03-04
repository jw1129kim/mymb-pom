package com.mymb.platform.api.repositories;

import com.mymb.platform.api.model.nft.NftInfo;
import com.mymb.platform.api.model.nft.NftInfoResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NftRepository extends MongoRepository<NftInfoResult, String> {
    NftInfoResult findNftInfoResultByTokenId(String name);
}
