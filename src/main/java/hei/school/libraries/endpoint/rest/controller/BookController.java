package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.entity.Book;
import hei.school.libraries.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping
  public ResponseEntity<List<Book>> getAllBooks() {
    return ResponseEntity.ok(bookService.getAllBooks());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable String id) {
    return ResponseEntity.ok(bookService.getBookById(id));
  }

  @PostMapping
  public ResponseEntity<Book> createBook(@RequestBody Book book) {
    return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(book));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book book) {
    return ResponseEntity.ok(bookService.patchBook(id, book));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable String id) {
    bookService.deleteBook(id);
    return ResponseEntity.noContent().build();
  }
}
