package com.nielo.demomongodb.demomongodb.repository;

import com.nielo.demomongodb.demomongodb.entity.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {
}
