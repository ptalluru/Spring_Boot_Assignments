package com.cg.mongo.mongodemo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.cg.mongo.mongodemo.entity.Pets;

public interface PetsRepository extends MongoRepository<Pets, String>{
}
