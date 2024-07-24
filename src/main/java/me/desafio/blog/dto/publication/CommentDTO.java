package me.desafio.blog.dto.publication;

import me.desafio.blog.entities.CommentEntity;
import me.desafio.blog.entities.PublicationEntity;

import java.util.Date;

public record CommentDTO(String id, String content, String authorId, PublicationDTO publicationId, Date createdAt) {

    public static CommentDTO commentEntity(CommentEntity commentEntity, PublicationEntity publicationEntity) {

        return new CommentDTO(
                commentEntity.getId(),
                commentEntity.getContent(),
                commentEntity.getAuthorId(),
                PublicationDTO.publicationEntity(publicationEntity),
                commentEntity.getCreatedAt()
        );
    }

}
