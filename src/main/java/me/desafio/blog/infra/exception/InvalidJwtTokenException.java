package me.desafio.blog.infra.exception;


public class InvalidJwtTokenException extends RuntimeException {
    public InvalidJwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}