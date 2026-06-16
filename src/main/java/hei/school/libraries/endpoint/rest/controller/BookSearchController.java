package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.entity.Book;
import hei.school.libraries.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books/search")
@RequiredArgsConstructor
public class BookSearchController {
    private final BookSearchService bookSearchService;

    @GetMapping("/title")
    public ResponseEntity<List<Book>> searchByTitle(@RequestParam("q") String title) {
        try {
            List<Book> books = bookSearchService.searchByTitle(title);
            return ResponseEntity.status(HttpStatus.OK).body(books);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/isbn")
    public ResponseEntity<List<Book>> searchByIsbn(@RequestParam("q") String isbn) {
        try {
            List<Book> books = bookSearchService.searchByIsbn(isbn);
            return ResponseEntity.status(HttpStatus.OK).body(books);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
