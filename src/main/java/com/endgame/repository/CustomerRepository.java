package com.endgame.repository;

import java.util.List;

import com.endgame.dto.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {

  Customer findByFirstName(String firstName);
  List<Customer> findByLastName(String lastName);

}