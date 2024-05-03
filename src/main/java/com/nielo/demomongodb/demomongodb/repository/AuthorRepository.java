package com.nielo.demomongodb.demomongodb.repository;

import com.nielo.demomongodb.demomongodb.entity.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
}
