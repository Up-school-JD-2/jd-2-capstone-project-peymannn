package io.upschool.exception;

public class EmailNotValidException extends BusinessException{
    public EmailNotValidException(String message) {
        super(message);
    }
}
