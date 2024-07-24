package me.desafio.blog.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @MongoId(FieldType.STRING)
    private String id;
    private String email;
    private String password;

    public UserEntity(String email, String password, String id) {
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
