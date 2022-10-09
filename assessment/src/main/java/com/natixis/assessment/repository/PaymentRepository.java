package com.natixis.assessment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.natixis.assessment.model.Payment;

public interface PaymentRepository extends MongoRepository<Payment,String>{

}
