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
    return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable String id) {
    try {
      Book book = bookService.getBookById(id);
      return ResponseEntity.status(HttpStatus.OK).body(book);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping
  public ResponseEntity<Book> createBook(@RequestBody Book book) {
    Book createdBook = bookService.createBook(book);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book book) {
    try {
      Book updated = bookService.patchBook(id, book);
      return ResponseEntity.status(HttpStatus.OK).body(updated);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable String id) {
    try {
      bookService.deleteBook(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
