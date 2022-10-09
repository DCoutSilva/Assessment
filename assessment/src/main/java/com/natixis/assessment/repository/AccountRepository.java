package com.natixis.assessment.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.natixis.assessment.model.Account;
public interface AccountRepository extends MongoRepository<Account, String> {

	List<Account> findByAccountNameContaining(String name);
}
