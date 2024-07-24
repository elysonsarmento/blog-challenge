package me.desafio.blog.repositories;

import me.desafio.blog.entities.PublicationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PublicationRepository extends MongoRepository<PublicationEntity, String> {
}
