package me.desafio.blog.dto.publication;

import jakarta.validation.constraints.NotEmpty;
import me.desafio.blog.entities.PublicationEntity;

import java.util.Date;
import java.util.List;

public record PublicationDTO(String id, String title, String content,
                             String authorId, List<String> tags,
                             Date createdAt, Date updatedAt,
                             List<String> commentsID
                             ) {


    public static PublicationDTO publicationEntity(PublicationEntity publicationEntity) {
        return new PublicationDTO(publicationEntity.getId(), publicationEntity.getTitle(),
                publicationEntity.getContent(), publicationEntity.getAuthorId(), publicationEntity.getTags(),
                publicationEntity.getCreatedAt(), publicationEntity.getUpdatedAt(), publicationEntity.getComments());
    }

    public PublicationEntity publicationEntity() {
        return new PublicationEntity(title, content, authorId, tags);
    }
}
