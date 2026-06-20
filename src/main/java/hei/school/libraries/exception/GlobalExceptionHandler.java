package hei.school.libraries.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException e) {
    return buildError(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<Map<String, Object>> handleForbidden(ForbiddenException e) {
    return buildError(HttpStatus.FORBIDDEN, e.getMessage());
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException e) {
    return buildError(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(InsufficientStockException.class)
  public ResponseEntity<Map<String, Object>> handleInsufficientStock(InsufficientStockException e) {
    return buildError(HttpStatus.CONFLICT, e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneric(Exception e) {
    return buildError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
  }

  private ResponseEntity<Map<String, Object>> buildError(HttpStatus status, String message) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    body.put("message", message);
    body.put("timestamp", Instant.now().toString());
    return ResponseEntity.status(status).body(body);
  }
}
