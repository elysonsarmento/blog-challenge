package me.desafio.blog.repositories;

import me.desafio.blog.entities.CommentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<CommentEntity, String> {}
