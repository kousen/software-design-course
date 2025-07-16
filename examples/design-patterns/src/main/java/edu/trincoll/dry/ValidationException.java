package edu.trincoll.dry;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}