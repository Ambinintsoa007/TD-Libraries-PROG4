package hei.school.libraries.endpoint.rest.controller;

import hei.school.libraries.entity.BookCopy;
import hei.school.libraries.service.BookCopyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookCopies")
@RequiredArgsConstructor
public class BookCopyController {

  private final BookCopyService bookCopyService;

  @GetMapping
  public ResponseEntity<List<BookCopy>> getAllBookCopies() {
    return ResponseEntity.status(HttpStatus.OK).body(bookCopyService.getAllBookCopies());
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookCopy> getBookCopyById(@PathVariable String id) {
    try {
      BookCopy bookCopy = bookCopyService.getBookCopyById(id);

      return ResponseEntity.status(HttpStatus.OK).body(bookCopy);

    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping
  public ResponseEntity<BookCopy> createBookCopy(@RequestBody BookCopy bookCopy) {
    BookCopy createdBookCopy = bookCopyService.createBookCopy(bookCopy);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdBookCopy);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<BookCopy> patchBookCopy(
      @PathVariable String id, @RequestBody BookCopy bookCopy) {

    try {
      BookCopy updatedBookCopy = bookCopyService.patchBookCopy(id, bookCopy);

      return ResponseEntity.status(HttpStatus.OK).body(updatedBookCopy);

    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBookCopy(@PathVariable String id) {
    try {
      bookCopyService.deleteBookCopy(id);

      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
