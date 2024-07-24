package me.desafio.blog.dto.publication;

import jakarta.validation.constraints.NotEmpty;
import me.desafio.blog.entities.CommentEntity;

public record CommentCreateDTO(@NotEmpty String content, @NotEmpty String authorId, @NotEmpty String publicationId) {

    public CommentCreateDTO {
        if (content.isBlank()) {
            throw new IllegalArgumentException("Content is required");
        }
        if (authorId.isBlank()) {
            throw new IllegalArgumentException("AuthorId is required");
        }
        if (publicationId.isBlank()) {
            throw new IllegalArgumentException("PublicationId is required");
        }
    }

    public CommentEntity commentEntity() {
        return new CommentEntity(content, authorId, publicationId);
    }

}
