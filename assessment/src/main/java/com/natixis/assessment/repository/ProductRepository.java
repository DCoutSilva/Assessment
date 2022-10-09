package com.natixis.assessment.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.natixis.assessment.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

	List<Product> findByNameContaining(String name);

}
