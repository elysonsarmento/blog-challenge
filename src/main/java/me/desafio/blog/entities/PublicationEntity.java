package me.desafio.blog.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "publication")
public class PublicationEntity {

    @MongoId(FieldType.STRING)
    private String id;


    private String title;
    private String content;
    private String authorId;
    private List<String> tags;
    private List<String> comments = new ArrayList<>();
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate()
    private Date updatedAt;


    public PublicationEntity( String title, String content, String authorId, List<String> tags) {

        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.tags = tags;
    }

    public void addComment(String commentId) {
            this.comments.add(commentId);
    }

}
