package io.upschool.exception;

public class CardNotValidException extends BusinessException{
    public CardNotValidException(String message) {
        super(message);
    }
}
