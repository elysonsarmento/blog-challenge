package me.desafio.blog.dto.publication;

import jakarta.validation.constraints.NotEmpty;
import me.desafio.blog.entities.PublicationEntity;

import java.util.List;

public record PublicationCreateDTO(String title,
                                   @NotEmpty(message = "Campo CONTENT não pode ficar nulo") String content,
                                   @NotEmpty(message = "Campo AUTHORID não pode ficar nulo") String authorId,
                                   List<String> tags) {
    public PublicationEntity publicationEntity() {
        return new PublicationEntity( title, content, authorId, tags);
    }
}
