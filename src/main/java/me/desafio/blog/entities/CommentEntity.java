package me.desafio.blog.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "comment")
public class CommentEntity {
    @MongoId(FieldType.STRING)
    private String id;
    private String content;
    private String authorId;

    private PublicationEntity publication;
    @DBRef
    private String publicationId;

    @CreatedDate
    private Date createdAt;

    public CommentEntity(String content, String authorId, String publicationId) {
        this.content = content;
        this.authorId = authorId;
        this.publication = new PublicationEntity();
        this.publication.setId(publicationId);
    }
}
