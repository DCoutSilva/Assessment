package com.natixis.assessment.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.natixis.assessment.model.User;

public interface UserRepository extends MongoRepository<User, String>{

	List<User> findByNameContaining(String name);

}
