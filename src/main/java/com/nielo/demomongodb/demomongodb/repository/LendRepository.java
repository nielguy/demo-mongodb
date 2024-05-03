package com.nielo.demomongodb.demomongodb.repository;

import com.nielo.demomongodb.demomongodb.entity.Book;
import com.nielo.demomongodb.demomongodb.entity.Lend;
import com.nielo.demomongodb.demomongodb.entity.LendStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LendRepository extends MongoRepository<Lend, String> {
    Optional<Lend> findByBookAndStatus(Book book, LendStatus status);
}
