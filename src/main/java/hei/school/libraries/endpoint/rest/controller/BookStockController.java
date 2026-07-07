package hei.school.libraries.endpoint.rest.controller;


import hei.school.libraries.endpoint.rest.controller.model.BookStockResponse;
import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.entity.enums.Status;
import hei.school.libraries.service.BookCopyService;
import hei.school.libraries.service.BookStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookStockController {
    private final BookStockService bookStockService;
    private final BookCopyService bookCopyService;

    @GetMapping("/{bookId}/stock")
    public ResponseEntity<BookStockResponse> getStock(@PathVariable String bookId) {
        if (!isValidUuid(bookId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            BookStockResponse response = bookStockService.getStock(bookId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{bookId}/copies")
    public ResponseEntity<List<BookCopy>> getCopies(
            @PathVariable String bookId,
            @RequestParam(value = "status", required = false) Status status) {
        if (!isValidUuid(bookId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            List<BookCopy> copies = bookCopyService.getCopies(bookId, status);
            return ResponseEntity.status(HttpStatus.OK).body(copies);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private boolean isValidUuid(String id) {
        try {
            UUID.fromString(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
