package com.laotek.intelligentdraw.dataserver.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.laotek.intelligentdraw.dataserver.doc.UserAccount;

@Deprecated
public interface UserAccountRepository extends MongoRepository<UserAccount, Integer> {

}
