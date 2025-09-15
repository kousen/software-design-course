package edu.trincoll.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    int status,
    String code,
    String message,
    LocalDateTime timestamp,
    String path,
    List<String> errors
) {
    public ErrorResponse(int status, String code, String message, LocalDateTime timestamp, String path) {
        this(status, code, message, timestamp, path, null);
    }

    public static ErrorResponse notFound(String resource, Long id) {
        return new ErrorResponse(
            404,
            "RESOURCE_NOT_FOUND",
            resource + " with id " + id + " not found",
            LocalDateTime.now(),
            null,
            null
        );
    }

    public static ErrorResponse badRequest(List<String> errors) {
        return new ErrorResponse(
            400,
            "VALIDATION_FAILED",
            "Validation failed",
            LocalDateTime.now(),
            null,
            errors
        );
    }

    public static ErrorResponse internalError() {
        return new ErrorResponse(
            500,
            "INTERNAL_ERROR",
            "An unexpected error occurred",
            LocalDateTime.now(),
            null,
            null
        );
    }
}