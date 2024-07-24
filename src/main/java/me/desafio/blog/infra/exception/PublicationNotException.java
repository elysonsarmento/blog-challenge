package me.desafio.blog.infra.exception;

public class PublicationNotException extends RuntimeException {
    public PublicationNotException(String id) {
        super("Publication not found with id: " + id);
    }


}
