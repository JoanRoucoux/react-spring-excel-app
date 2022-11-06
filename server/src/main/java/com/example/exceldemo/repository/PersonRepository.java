package com.example.exceldemo.repository;

import com.example.exceldemo.common.model.PersonDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<PersonDocument, Integer> {
}