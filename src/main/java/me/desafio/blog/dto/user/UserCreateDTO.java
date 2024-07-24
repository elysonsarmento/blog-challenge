package me.desafio.blog.dto.user;

import jakarta.validation.constraints.NotEmpty;
import me.desafio.blog.entities.UserEntity;

public record UserCreateDTO(@NotEmpty(message = "Campo EMAIL não pode ficar vazio")  String email,
                            @NotEmpty(message = "Campo PASSWORD não pode ficar vazio") String password

                            ) {



    public UserEntity toEntity() {
        return new UserEntity(email, password);
    }
}
